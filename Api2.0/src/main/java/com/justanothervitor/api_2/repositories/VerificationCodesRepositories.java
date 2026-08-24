package com.justanothervitor.api_2.repositories;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VerificationCodesRepositories extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByCodeAndType(String code, VerificationType type);

    Optional<VerificationCode> findByUserAndTypeAndVerifiedFalse(
            User user, VerificationType type);

    List<VerificationCode> findByUserAndType(User user, VerificationType type);

    void deleteByExpiryDateBefore(LocalDateTime date);

    void deleteByUserAndType(User user, VerificationType type);

    @Query("SELECT COUNT(v) FROM verification_codes v WHERE v.user = :user " +
            "AND v.type = :type AND v.createdAt > :since")
    long countRecentCodesByUserAndType(
            @Param("user") User user,
            @Param("type") VerificationType type,
            @Param("since") LocalDateTime since);

}
