package com.justanothervitor.api_2.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CodeGeneratorService {

    private static final String NUMERIC_CHARS="0123456789";
    private static final String ALPHANUMERIC_CHARS="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public String generateNumericCode(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i =0; i < length;i++){
            code.append(NUMERIC_CHARS.charAt(random.nextInt(NUMERIC_CHARS.length())));
        }
        return code.toString();
    }

    public String generateAlphanumericCode(int length){
        StringBuilder code = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            code.append(ALPHANUMERIC_CHARS.charAt(random.nextInt(ALPHANUMERIC_CHARS.length())));
        }
        return code.toString();
    }

    public String generateFormattedCode(String pattern)
    {
        StringBuilder code = new StringBuilder();
        for(char c : pattern.toCharArray()){
            if(c == 'X'){
                code.append((char) ('A'+random.nextInt(26)));
            } else if(c == '9'){
                code.append(random.nextInt(10));
            }else{
                code.append(c);
            }
        }
        return code.toString();
    }

    public String generateTOTP(String secret,long timeStep)
    {
        try{
            long counter = System.currentTimeMillis()/(timeStep*1000);
            return String.format("%06d",counter%1000000);
        }catch(Exception e){
            throw new RuntimeException("Erro ao gerar TOTP",e);
        }
    }

}
