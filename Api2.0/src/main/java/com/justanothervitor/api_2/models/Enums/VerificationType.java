package com.justanothervitor.api_2.models.Enums;

import lombok.Getter;

@Getter
public enum VerificationType {
    EMAIL_VERIFICATION("Verificação de Email"),
    PASSWORD_RESET("Recuperação de Senha"),
    TWO_FACTOR_VERIFICATION("Autenticação em Dois Fatores"),
    CHANGE_EMAIL_VERIFICATION("Alteração de Email");

    private final String description;

    VerificationType(String description) {
        this.description = description;
    }

}
