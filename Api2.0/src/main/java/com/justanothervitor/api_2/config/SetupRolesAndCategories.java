package com.justanothervitor.api_2.config;

import com.justanothervitor.api_2.models.Category;
import com.justanothervitor.api_2.models.Enums.ERole;
import com.justanothervitor.api_2.models.Enums.EnumTag;
import com.justanothervitor.api_2.models.Role;
import com.justanothervitor.api_2.repositories.CategoryRepositories;
import com.justanothervitor.api_2.repositories.RoleRepositories;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SetupRolesAndCategories implements CommandLineRunner {
    private Logger log = LoggerFactory.getLogger(SetupRolesAndCategories.class);

    private final RoleRepositories roleRepositories;
    private final CategoryRepositories categoryRepositories;

    @Override
    public void run(String... args) throws Exception {

       List<Role> allRoles = roleRepositories.findAll();
       List<Category> allCategories = categoryRepositories.findAll();

       log.info("Quantidade de roles {}", allRoles.size());
       if(allRoles.size() != ERole.values().length && allRoles.isEmpty()) {
           Role normalRole = new Role();
           normalRole.setERole(ERole.USER);
           normalRole.setName("User");
           roleRepositories.save(normalRole);
           Role adminRole = new Role();
           adminRole.setERole(ERole.ADMIN);
           adminRole.setName("Admin");
           roleRepositories.save(adminRole);
           allRoles =  roleRepositories.findAll();
           log.info("Roles salvas {}", allRoles);
       }else if(allRoles.size() != ERole.values().length && allRoles.size() == 1) {
           if(allRoles.get(0).getERole().equals(ERole.ADMIN)) {
               Role adminRole = new Role();
               adminRole.setERole(ERole.ADMIN);
               adminRole.setName("Admin");
               roleRepositories.save(adminRole);
               allRoles =  roleRepositories.findAll();
           }
           Role userRole = new Role();
           userRole.setERole(ERole.USER);
           userRole.setName("User");
           roleRepositories.save(userRole);
           allRoles =  roleRepositories.findAll();
       }
        log.info("Quantidade de Categorias {}", allCategories.size());
        if(allCategories.size() != EnumTag.values().length && allCategories.isEmpty()) {
            Category billsCategory = new Category();
            billsCategory.setName("Bills");
            billsCategory.setTag(EnumTag.BILLS);
            categoryRepositories.save(billsCategory);
            Category entertainmentsCategory = new Category();
            entertainmentsCategory.setName("Entertainments");
            entertainmentsCategory.setTag(EnumTag.ENTERTAINMENT);
            categoryRepositories.save(entertainmentsCategory);
            Category upkeepsCategory = new Category();
            upkeepsCategory.setName("Upkeep");
            upkeepsCategory.setTag(EnumTag.UPKEEP);
            categoryRepositories.save(upkeepsCategory);
            Category foodCategory = new Category();
            foodCategory.setName("Food");
            foodCategory.setTag(EnumTag.FOOD);
            categoryRepositories.save(foodCategory);
            Category eletronicsCategory = new Category();
            eletronicsCategory.setName("Eletronics");
            eletronicsCategory.setTag(EnumTag.ELECTRONICS);
            categoryRepositories.save(eletronicsCategory);
            Category fuelCategory = new Category();
            fuelCategory.setName("Fuel");
            fuelCategory.setTag(EnumTag.FUEL);
            categoryRepositories.save(fuelCategory);
        }
        log.info("Quantidade de Categorias {}", allCategories.size());
    }
}
