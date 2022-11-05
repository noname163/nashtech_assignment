package com.nash.assignment.controllers.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nash.assignment.constant.ProductStatus;
import com.nash.assignment.dto.request.CategoriesDto;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.services.CategoriesServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@WebMvcTest(value=CategoryController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@AutoConfigureMockMvc(addFilters =false)
public class CategoryControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean
    private CategoriesServiceImpl categoriesServiceImpl;
    @Test
    void insertCategory() throws Exception {
        CategoriesDto categoriesDto = new CategoriesDto();
        categoriesDto.setName("test");
        categoriesDto.setDescription("test");
        when(categoriesServiceImpl.insertCategory(categoriesDto)).thenReturn(categoriesDto);

        RequestBuilder request = MockMvcRequestBuilders.post("/categories")
        .content(objectMapper.writeValueAsString(categoriesDto))
        .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(result.getResponse().getContentAsString(),
                is("{\"name\":\"test\",\"description\":\"test\"}"));
    }
}
