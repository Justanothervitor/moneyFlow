package com.justanothervitor.api_2.services;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.VerificationCode;
import com.justanothervitor.api_2.models.payloads.response.VerificationResponse;
import com.justanothervitor.api_2.repositories.UserRepositories;
import com.justanothervitor.api_2.repositories.VerificationCodesRepositories;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class VerificationService {

    private static final Logger logger = LoggerFactory.getLogger(VerificationService.class);

    private final VerificationCodesRepositories verificationCodesRepositories;
    private final UserRepositories userRepositories;
    private final CodeGeneratorService codeGeneratorService;
    private final EmailService emailService;

    @Value("${app.verification.code-length}")
    private int codeLength;
    @Value("${app.verification.code-expiration-minutes}")
    private int expirationMinutes;
    @Value("${app.verification.max-attempts}")
    private int maxAttempts;
    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Autowired
    public VerificationService(VerificationCodesRepositories verificationCodesRepositories, UserRepositories userRepositories, CodeGeneratorService codeGeneratorService, EmailService emailService) {
        this.verificationCodesRepositories = verificationCodesRepositories;
        this.userRepositories = userRepositories;
        this.codeGeneratorService = codeGeneratorService;
        this.emailService = emailService;
    }

    public VerificationResponse generateAndSendCode(User user, VerificationType type) {

        LocalDateTime oneHourAgo = LocalDateTime.now().minusMinutes(1);
        long recentCodes = verificationCodesRepositories.countRecentCodesByUserAndType(user,type,oneHourAgo);
        if (recentCodes >=3) {
            throw new RuntimeException("Muitas tentativas. Aguarde antes de solicitar um novo código");
        }

        verificationCodesRepositories.deleteByUserAndType(user,type);

        String code = codeGeneratorService.generateAlphanumericCode(codeLength);
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(expirationMinutes);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(user);
        verificationCode.setCode(code);
        verificationCode.setType(type);
        verificationCode.setExpiryDate(expiryDate);
        verificationCodesRepositories.save(verificationCode);

        sendVerificationEmail(user,code,type,expiryDate);
        logger.info("Código de verificação gerado para usuário: {} (tipo: {})",user.getEmail(),type);

        return new VerificationResponse(true,"Código enviado para"+ maskEmail(user.getEmail()),expiryDate);
    }

    public boolean verifyCode(User user, String code, VerificationType type){
        Optional<VerificationCode> optionalCode = verificationCodesRepositories.findByUserAndTypeAndVerifiedFalse(user,type);

        if(optionalCode.isEmpty()){
            throw new RuntimeException("Código não encontrado ou já utilizado");
        }

        VerificationCode verificationCode = optionalCode.get();

        if(verificationCode.isExpired()){
            throw new RuntimeException("Código expirado. Solicite um novo código.");
        }

        if(verificationCode.isMaxAttemptsReached(this.maxAttempts)){
            throw new RuntimeException("Número máximo de tentativas excedido. Solicite um novo código.");
        }

        verificationCode.setAttemps(verificationCode.getAttemps() + 1);
        int remainingAttempts = verificationCode.getAttemps();

        if(!verificationCode.getCode().equals(code)){
            verificationCodesRepositories.save(verificationCode);
            throw new RuntimeException("Código inválido. Tente novamente");
        }
        if(remainingAttempts == 0)
        {
            throw new RuntimeException("Número de tentativas excedido!\n Tente novamente mais tarde!");
        }

        verificationCode.setVerified(true);
        verificationCode.setVerifiedAt(LocalDateTime.now());
        verificationCodesRepositories.save(verificationCode);

        updateUserAfterVerification(user,type);

        logger.info("Código verificado com sucesso para usuário: {} (tipo: {})",user.getEmail(),type);
        return true;
    }

    private void sendVerificationEmail(User user, String code, VerificationType type,LocalDateTime expiryDate){
        Map<String,Object> variables = new HashMap<>();
        variables.put("user",user.getUsername());
        variables.put("code",code);
        variables.put("expiryDate",expiryDate);
        variables.put("type",type);
        variables.put("frontendUrl",frontendUrl);

        String subject = getEmailSubject(type);
        String template = getEmailTemplate(type);

        emailService.sendHtmlAsync(user.getEmail(),subject,template,variables);
    }

    private void updateUserAfterVerification(User user,VerificationType type){
       if(type.equals(VerificationType.EMAIL_VERIFICATION)){
           user.setEmailVerified(true);
           userRepositories.save(user);
       }
    }

    private String getEmailSubject(VerificationType type){
        return switch (type) {
            case EMAIL_VERIFICATION -> "Verifique seu email";
            case PASSWORD_RESET -> "Recuperação de senha";
            case TWO_FACTOR_VERIFICATION -> "Código de Autenticação";
            case CHANGE_EMAIL_VERIFICATION -> "Confirme alteração de email";
            default -> "Código de verificação";
        };
    }

    private String getEmailTemplate(VerificationType type){
        return switch (type) {
            case EMAIL_VERIFICATION -> "email-verification";
            case PASSWORD_RESET -> "password-reset";
            case TWO_FACTOR_VERIFICATION -> "two-factor";
            default -> "verification-code";
        };
    }

    private String maskEmail(String email){
        String[] parts = email.split("@");
        if(parts.length != 2)return email;

        String username = parts[0];
        String domain = parts[1];

        if(username.length() == 3){
            return username.charAt(0)+"***@"+domain;
        }
        return username.substring(0,3)+"***@"+domain;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void clearExpiredCodes()
    {
        LocalDateTime now = LocalDateTime.now();
        verificationCodesRepositories.deleteByExpiryDateBefore(now);
        logger.info("Códigos expirados removidos");
    }

    public VerificationCode findByCode(String code,VerificationType type){
        return verificationCodesRepositories.findByCodeAndType(code,type).orElseThrow(()-> new RuntimeException("Código inválido!"));
    }

}
