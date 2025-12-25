package com.justanothervitor.api_2.repositories;

import com.justanothervitor.api_2.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositories extends JpaRepository<Role, Integer> {

   Role findRoleByName(String name);

}
