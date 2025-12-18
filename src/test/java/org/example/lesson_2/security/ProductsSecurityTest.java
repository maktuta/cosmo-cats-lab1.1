package org.example.lesson_2.security;

import org.example.lesson_2.controller.ProductsController;
import org.example.lesson_2.dto.ProductDto;
import org.example.lesson_2.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductsController.class)
@Import({SecurityConfig.class, JwtDecoderConfig.class})
class ProductsSecurityTest {

    @MockBean
    ProductService productService;

    @jakarta.annotation.Resource
    MockMvc mvc;

    @Test
    void shouldReturn401WithoutToken() throws Exception {
        mvc.perform(get("/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowWithMockJwt() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(
                new ProductDto(1, "Star Yarn", 10.0f, 1)
        ));

        mvc.perform(get("/products")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }
}
