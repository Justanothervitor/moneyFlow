package com.justanothervitor.api_2.models.Enums;

import jakarta.persistence.Table;

@Table(name="authProvider")
public enum AuthProvider {
    GOOGLE,
    FACEBOOK,
    LOCAL
}
