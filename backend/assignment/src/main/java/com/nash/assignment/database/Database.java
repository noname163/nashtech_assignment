package com.nash.assignment.database;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nash.assignment.constant_variable.RoleEnum;
import com.nash.assignment.constant_variable.StatusEnum;
import com.nash.assignment.modal.Accounts;
import com.nash.assignment.modal.Categories;
import com.nash.assignment.modal.Products;
import com.nash.assignment.modal.Roles;
import com.nash.assignment.modal.Status;
import com.nash.assignment.service.AccountsServiceImpl;
import com.nash.assignment.service.CategoriesServiceImpl;
import com.nash.assignment.service.ProductsServiceImpl;
import com.nash.assignment.service.RolesServicesImpl;
import com.nash.assignment.service.StatusServiceImpl;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@RequiredArgsConstructor
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Autowired AccountsServiceImpl accountsServiceImpl;
    @Bean
    CommandLineRunner initDatabase( StatusServiceImpl statusServiceImpl,
            RolesServicesImpl rolesServicesImpl, ProductsServiceImpl productsServiceImpl,
            CategoriesServiceImpl categoriesServiceImpl) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Roles adminRole = new Roles(RoleEnum.ROLE_ADMIN.name());
                Roles userRole = new Roles(RoleEnum.ROLE_USER.name());
                System.out.println("Role: " + RoleEnum.ROLE_ADMIN.name());
                logger.info("Insert Roles ", rolesServicesImpl.insertRole(adminRole));
                logger.info("Insert Roles ", rolesServicesImpl.insertRole(userRole));

                Status activeStatus = new Status(StatusEnum.Active.name());
                Status unactiveStatus = new Status(StatusEnum.Deactivate.name());

                logger.info("Insert Status ", statusServiceImpl.insertStatus(activeStatus));
                logger.info("Insert Status ", statusServiceImpl.insertStatus(unactiveStatus));

                Accounts adminAccount = new Accounts("0345323543", "Admin", "Admin User", "admin", adminRole,
                        activeStatus);
                Accounts userAccount = new Accounts("0345323542", "User", "Customer User", "user", userRole,
                        unactiveStatus);

                logger.info("Insert Account ", accountsServiceImpl.insertAccounts(adminAccount));
                logger.info("Insert Account ", accountsServiceImpl.insertAccounts(userAccount));

                Categories categories1 = new Categories("Categories 1");
                Categories categories2 = new Categories("Categories 2");

                logger.info("Insert Categories ", categoriesServiceImpl.insertCategories(categories1));
                logger.info("Insert Categories ", categoriesServiceImpl.insertCategories(categories2));

                Products product1 = new Products("Product1", null, "12000", activeStatus, categories1);
                Products product2 = new Products("Product2", null, "14000", activeStatus, categories2);

                logger.info("Insert Product ", productsServiceImpl.insertProduct(product1));
                logger.info("Insert Product ", productsServiceImpl.insertProduct(product2));

            }
        };
    }
}
