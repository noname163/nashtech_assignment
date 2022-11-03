package com.nash.assignment.database;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nash.assignment.constant.RoleEnum;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Image;
import com.nash.assignment.modal.Order;
import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.modal.Product;
import com.nash.assignment.modal.Role;
import com.nash.assignment.services.AccountsServiceImpl;
import com.nash.assignment.services.CategoriesServiceImpl;
import com.nash.assignment.services.ImageServiceImpl;
import com.nash.assignment.services.OrderDetailServiceImpl;
import com.nash.assignment.services.OrderServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;
import com.nash.assignment.services.RolesServicesImpl;
import com.nash.assignment.services.interfaces.OrderDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Database {
        private static final Logger logger = LoggerFactory.getLogger(Database.class);
        @Autowired
        AccountsServiceImpl accountsServiceImpl;
        @Autowired
        ModelMapper modelMapper;
        @Autowired
        OrderDetailServiceImpl orderDetailServiceImpl;
        @Autowired
        OrderServiceImpl orderServiceImpl;

        @Bean
        CommandLineRunner initDatabase(
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

                                Account adminAccount = new Account("0345323543", "Admin", "Admin User", "admin",
                                                adminRole,
                                                StatusEnum.ACTIVE);
                                adminAccount.setEmail("admin@gmail.com");
                                Account userAccount = new Account("0345323542", "User", "Customer User", "user",
                                                userRole,
                                                StatusEnum.ACTIVE);
                                userAccount.setEmail("user@gmail.com");

                                logger.info("Insert Account ",
                                                accountsServiceImpl.insert(adminAccount));
                                logger.info("Insert Account ",
                                                accountsServiceImpl.insert(userAccount));

                                Category categories1 = new Category("Categories 1");
                                Category categories2 = new Category("Categories 2");

                                logger.info("Insert Categories ", categoriesServiceImpl.insertCategories(categories1));
                                logger.info("Insert Categories ", categoriesServiceImpl.insertCategories(categories2));

                                Product product1 = new Product("Product1", "12000", StatusEnum.ACTIVE, categories1);
                                Product product2 = new Product("Product2", "14000", StatusEnum.ACTIVE, categories2);
                                logger.info("Insert Product ",
                                                productsServiceImpl.insertProduct1(product1));
                                logger.info("Insert Product ",
                                                productsServiceImpl.insertProduct1(product2));

                                // Image image1 = new Image("Test1", product1);
                                // Image image2 = new Image("Test2", product1);
                                // logger.info("Insert Image ", imageServiceImpl.insertImage(image1));
                                // logger.info("Insert Image ", imageServiceImpl.insertImage(image2));

                                Order order = new Order("Test", "Test", userAccount);
                                logger.info("Insert Order", orderServiceImpl.insert(order));

                                OrderDetail orderDetail = new OrderDetail(1, product2, order, 1);
                                logger.info("Insert OrderDetail ", orderDetailServiceImpl.insert(orderDetail));
                        }
                };
        }
}
