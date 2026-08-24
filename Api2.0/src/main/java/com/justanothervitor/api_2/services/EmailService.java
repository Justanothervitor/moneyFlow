package com.justanothervitor.api_2.services;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.from-name}")
    private String fromName;

    public void sendSimpleEmail(String to, String subject, String body){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            logger.info("Email simples enviado com sucesso:{}",to);
        }catch(Exception e){
            logger.error("Erro ao enviar o email para: {}",to,e);
            throw new RuntimeException("Falha ao enviar o email",e);
        }
    }

    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setFrom(new InternetAddress(fromEmail,fromName));
            helper.setTo(to);
            helper.setSubject(subject);
            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process(templateName,context);
            helper.setText(htmlContent,true);
            mailSender.send(message);
            logger.info("Email html enviado com sucesso:{}",to);
        } catch (Exception e) {
            logger.error("Erro ao enviar o email para: {}",to,e);
            throw new RuntimeException("Falha ao enviar o email",e);
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String body, File attachment){

        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setFrom(new InternetAddress(fromEmail,fromName));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);
            helper.addAttachment(attachment.getName(),attachment);

            mailSender.send(message);
            logger.info("Email com anexo enviado com sucesso:{}",to);
        }catch(Exception e){
            logger.info("Erro ao enviar o email para: {}",to,e);
            throw new RuntimeException("Falha ao enviar o email",e);
        }

    }

    @Async("emailTaskExecutor")
    public CompletableFuture<Boolean> sendHtmlAsync(String to, String subject, String template,Map<String,Object> variables){
        try{
            sendHtmlEmail(to,subject,template,variables);
            return CompletableFuture.completedFuture(true);
        }catch(Exception e){
            logger.error("Erro ao enviar email assíncrono",e);
            return CompletableFuture.completedFuture(false);
        }
    }

}
