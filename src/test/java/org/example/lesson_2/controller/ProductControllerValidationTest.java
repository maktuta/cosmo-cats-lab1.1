package org.example.lesson_2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lesson_2.dto.ProductDto;
import org.example.lesson_2.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
class ProductControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void create_validBody_returns201() throws Exception {
        ProductDto request = new ProductDto(0, "star milk", 10.0f, 1);
        ProductDto response = new ProductDto(3, "star milk", 10.0f, 1);

        when(productService.createProduct(any(ProductDto.class))).thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_emptyName_returns400() throws Exception {
        String body = """
                {
                  "id": 0,
                  "name": "",
                  "price": 10.0,
                  "category": 1
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        org.mockito.Mockito.verify(productService, never()).createProduct(any());
    }

    @Test
    void create_priceZero_returns400() throws Exception {
        String body = """
                {
                  "id": 0,
                  "name": "star milk",
                  "price": 0,
                  "category": 1
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        org.mockito.Mockito.verify(productService, never()).createProduct(any());
    }

    @Test
    void create_nameWithoutCosmicWord_returns400() throws Exception {
        String body = """
                {
                  "id": 0,
                  "name": "regular milk",
                  "price": 10.0,
                  "category": 1
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        org.mockito.Mockito.verify(productService, never()).createProduct(any());
    }
}
