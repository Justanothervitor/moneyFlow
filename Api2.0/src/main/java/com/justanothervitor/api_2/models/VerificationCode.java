package com.justanothervitor.api_2.models;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity(name = "verification_codes")
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="userid",nullable = false)
    private User user;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationType type;

    @Column(name="expiry_date",nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private Boolean verified = false;

    @Column(nullable = false)
    private Integer attemps = 0;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="verified_at")
    private LocalDateTime verifiedAt;

    public VerificationCode() {}

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isMaxAttemptsReached(int maxAttempts) {
        return attemps >= maxAttempts;
    }


}
