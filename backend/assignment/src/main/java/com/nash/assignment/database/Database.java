package com.nash.assignment.database;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.nash.assignment.model.Accounts;
import com.nash.assignment.model.Categories;
import com.nash.assignment.model.Products;
import com.nash.assignment.model.Roles;
import com.nash.assignment.model.Status;
import com.nash.assignment.service.AccountsServiceImpl;
import com.nash.assignment.service.CategoriesServiceImpl;
import com.nash.assignment.service.ProductsServiceImpl;
import com.nash.assignment.service.RolesServicesImpl;
import com.nash.assignment.service.StatusServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    CommandLineRunner initDatabase(AccountsServiceImpl accountsServiceImpl, StatusServiceImpl statusServiceImpl,
            RolesServicesImpl rolesServicesImpl, ProductsServiceImpl productsServiceImpl,
            CategoriesServiceImpl categoriesServiceImpl) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Roles adminRole = new Roles("ROLE_ADMIN");
                Roles userRole = new Roles("ROLE_USER");

                logger.info("Insert Roles ", rolesServicesImpl.insertRole(adminRole));
                logger.info("Insert Roles ", rolesServicesImpl.insertRole(userRole));

                Status activeStatus = new Status("Active");
                Status unactiveStatus = new Status("Unactive");

                logger.info("Insert Status ", statusServiceImpl.insertStatus(activeStatus));
                logger.info("Insert Status ", statusServiceImpl.insertStatus(unactiveStatus));

                Accounts adminAccount = new Accounts("012345678", "Admin", "Admin User", "admin", adminRole,
                        activeStatus);
                Accounts userAccount = new Accounts("029384621", "User", "Customer User", "user", userRole,
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
