package com.nash.assignment.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.services.CategoriesServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

// @SpringBootTest
// @AutoConfigureMockMvc

@WebMvcTest(value = ProductConstroller.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class ProductConstrollerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductsServiceImpl productsServiceImpl;
    @MockBean
    private CategoriesServiceImpl categoriesServiceImpl;
    @MockBean
    private ProductMapper productMapper;

    @Test
    void DisplayProductController_ShouldReturnListProductDtoForUser() throws Exception {
        List<ProductDtoForUser> expected = new ArrayList<>();

        RequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/products");
        when(productsServiceImpl.getAllProducts()).thenReturn(expected);

        mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(expected.toString())));

    }

    @Test
    void displayProductByCategories_ShouldReturnListProductDto() throws Exception {
        List<ProductDtoForUser> expectedList = new ArrayList<>();
        ProductDtoForUser productDtoForUser = new ProductDtoForUser();
        expectedList.add(productDtoForUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:8080/products/by-categories/category");
        when(productsServiceImpl.getProductByCategories("category")).thenReturn(expectedList);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus(), is(HttpStatus.OK.value()));
        assertThat(result.getResponse().getContentAsString(),
                is(objectMapper.writeValueAsString(expectedList)));    
    }

    @Test
    void getAllCategories_ShouldReturnListCategory() throws Exception {
        List<Category> expected = new ArrayList<>();
        Category category = new Category();
        expected.add(category);
        when(categoriesServiceImpl.getAllCategories()).thenReturn(expected);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://localhost:8080/products/categories");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertThat(result.getResponse().getStatus(), is(HttpStatus.OK.value()));
        assertThat(result.getResponse().getContentAsString(),
                is(objectMapper.writeValueAsString(expected)));  
    }
}
