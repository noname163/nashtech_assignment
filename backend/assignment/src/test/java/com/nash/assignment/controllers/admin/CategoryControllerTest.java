package com.nash.assignment.controllers.admin;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nash.assignment.dto.request.CategoriesDto;
import com.nash.assignment.services.CategoriesServiceImpl;

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

        RequestBuilder request = MockMvcRequestBuilders.post("/admin/categories")
        .content(objectMapper.writeValueAsString(categoriesDto))
        .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(result.getResponse().getContentAsString(),
                is("{\"name\":\"test\",\"description\":\"test\"}"));
    }
}
