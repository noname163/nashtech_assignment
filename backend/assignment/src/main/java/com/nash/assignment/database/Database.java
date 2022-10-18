package com.nash.assignment.database;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Image;
import com.nash.assignment.modal.Product;
import com.nash.assignment.modal.Role;
import com.nash.assignment.modal.Status;
import com.nash.assignment.services.AccountsServiceImpl;
import com.nash.assignment.services.CategoriesServiceImpl;
import com.nash.assignment.services.ImageServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;
import com.nash.assignment.services.RolesServicesImpl;
import com.nash.assignment.services.StatusServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@RequiredArgsConstructor
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Autowired
    AccountsServiceImpl accountsServiceImpl;
    @Autowired ModelMapper modelMapper;

    @Bean
    CommandLineRunner initDatabase(StatusServiceImpl statusServiceImpl,
            RolesServicesImpl rolesServicesImpl, ProductsServiceImpl productsServiceImpl,
            CategoriesServiceImpl categoriesServiceImpl, ImageServiceImpl imageServiceImpl) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Role adminRole = new Role(RoleEnum.ROLE_ADMIN);
                Role userRole = new Role(RoleEnum.ROLE_USER);
                System.out.println("Role: " + RoleEnum.ROLE_ADMIN.name());
                logger.info("Insert Roles ", rolesServicesImpl.insertRole(adminRole));
                logger.info("Insert Roles ", rolesServicesImpl.insertRole(userRole));

                Status activeStatus = new Status(StatusEnum.ACTIVE.name());
                Status unactiveStatus = new Status(StatusEnum.DEACTIVE.name());

                logger.info("Insert Status ", statusServiceImpl.insertStatus(activeStatus));
                logger.info("Insert Status ", statusServiceImpl.insertStatus(unactiveStatus));

                Account adminAccount = new Account("0345323543", "Admin", "Admin User", "admin", adminRole,
                        StatusEnum.ACTIVE);
                Account userAccount = new Account("0345323542", "User", "Customer User", "user", userRole,
                        StatusEnum.DEACTIVE);

                logger.info("Insert Account ", accountsServiceImpl.insertAccounts(modelMapper.map(adminAccount, AccountDto.class)));
                logger.info("Insert Account ", accountsServiceImpl.insertAccounts(modelMapper.map(userAccount, AccountDto.class)));

                Category categories1 = new Category("Categories 1");
                Category categories2 = new Category("Categories 2");

                logger.info("Insert Categories ", categoriesServiceImpl.insertCategories(categories1));
                logger.info("Insert Categories ", categoriesServiceImpl.insertCategories(categories2));

                Product product1 = new Product("Product1", "12000", activeStatus, categories1);
                Product product2 = new Product("Product2", "14000", activeStatus, categories2);

                logger.info("Insert Product ", productsServiceImpl.insertProduct(product1,categories1.getName()));
                logger.info("Insert Product ", productsServiceImpl.insertProduct(product2,categories2.getName()));

                Image image1 = new Image("Test1", product1);
                Image image2 = new Image("Test2", product1);
                logger.info("Insert Image ", imageServiceImpl.insertImage(image1));
                logger.info("Insert Image ", imageServiceImpl.insertImage(image2));
            }
        };
    }
}
