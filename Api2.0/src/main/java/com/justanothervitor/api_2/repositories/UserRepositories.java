package com.justanothervitor.api_2.repositories;

import com.justanothervitor.api_2.models.Enums.AuthProvider;
import com.justanothervitor.api_2.models.Role;
import com.justanothervitor.api_2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String name);
    Optional<User> findByProviderAndProviderId(AuthProvider provider,String providerId);
    Optional<User> findByEmail(String email);

}
