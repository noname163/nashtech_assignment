package com.nash.assignment.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Category;
import com.nash.assignment.services.CategoriesServiceImpl;
import com.nash.assignment.services.PaginationServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;

// @SpringBootTest
// @AutoConfigureMockMvc

@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class ProductConstrollerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductsServiceImpl productsServiceImpl;
    @MockBean
    private CategoriesServiceImpl categoriesServiceImpl;
    @MockBean
    private ProductMapper productMapper;
    @MockBean
    PaginationServiceImpl paginationServiceImpl;

    @Test
    void DisplayProductController_ShouldReturnListProductDtoForUser() throws Exception {
        List<ProductDtoForUser> expected = new ArrayList<>();
        ProductDtoForUser productDto = new ProductDtoForUser();
        expected.add(productDto);
        RequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/products");
        when(productsServiceImpl.getAllProductsAvailble()).thenReturn(expected);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus(), is(HttpStatus.OK.value()));
        assertThat(result.getResponse().getContentAsString(),
                is( "[{\"id\":0,\"name\":null,\"price\":null,\"status\":null,\"description\":null,\"categories\":null,\"images\":[]}]"));
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
                is("[{\"id\":0,\"name\":null,\"price\":null,\"status\":null,\"description\":null,\"categories\":null,\"images\":[]}]"));    
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
                is("[{\"name\":null,\"description\":null}]"));  
    }
}
