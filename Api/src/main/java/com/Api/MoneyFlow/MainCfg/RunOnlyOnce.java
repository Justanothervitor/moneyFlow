package com.Api.MoneyFlow.MainCfg;

import com.Api.MoneyFlow.Domains.ERole;
import com.Api.MoneyFlow.Domains.RolesDomain;
import com.Api.MoneyFlow.Repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RunOnlyOnce implements CommandLineRunner {

    @Autowired
    protected RolesRepository rolesRepository;

    @Override
    public void run(String... args) throws Exception {

        if(rolesRepository.findRoleByName(ERole.NORMAL) == null)
        {
            RolesDomain NORMAL = new RolesDomain(null,"NORMAL",ERole.NORMAL);
            rolesRepository.saveRole(NORMAL);
        }
    }
}
