package org.example.lesson_2.service;

import org.example.lesson_2.domain.Product;
import org.example.lesson_2.dto.ProductDto;
import org.example.lesson_2.exception.ResourceNotFoundException;
import org.example.lesson_2.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductMapper mapper;
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = mock(ProductMapper.class);
        service = new ProductServiceImpl(mapper);
    }

    @Test
    void createProduct_assignsNewId_andReturnsDto() {
        ProductDto input = new ProductDto(0, "star milk", 10.0f, 1);

        Product entityFromMapper = new Product(0, input.getName(), input.getPrice(), input.getCategory());
        when(mapper.toEntity(any(ProductDto.class))).thenReturn(entityFromMapper);

        when(mapper.toDto(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            return new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getCategoryID());
        });

        ProductDto created = service.createProduct(input);

        assertNotNull(created);
        assertTrue(created.getId() > 0);
        assertEquals("star milk", created.getName());
        assertEquals(10.0f, created.getPrice());

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(mapper).toDto(captor.capture());
        assertEquals(created.getId(), captor.getValue().getId());
    }

    @Test
    void getAllProducts_returnsDtosForAllMockItems() {
        when(mapper.toDto(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            return new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getCategoryID());
        });

        List<ProductDto> all = service.getAllProducts();

        assertNotNull(all);
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(p -> p.getId() == 1));
        assertTrue(all.stream().anyMatch(p -> p.getId() == 2));
        verify(mapper, times(2)).toDto(any(Product.class));
    }

    @Test
    void getProductById_existing_returnsDto() {
        when(mapper.toDto(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            return new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getCategoryID());
        });

        ProductDto dto = service.getProductById(1);

        assertEquals(1, dto.getId());
        assertEquals("Laptop", dto.getName());
    }

    @Test
    void getProductById_missing_throwsResourceNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> service.getProductById(999));
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateProduct_existing_updatesNameAndPrice() {
        ProductDto update = new ProductDto(0, "galaxy laptop", 2000.0f, 1);

        when(mapper.toDto(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            return new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getCategoryID());
        });

        ProductDto updated = service.updateProduct(1, update);

        assertEquals(1, updated.getId());
        assertEquals("galaxy laptop", updated.getName());
        assertEquals(2000.0f, updated.getPrice());
    }

    @Test
    void updateProduct_missing_throwsResourceNotFound() {
        ProductDto update = new ProductDto(0, "galaxy laptop", 2000.0f, 1);
        assertThrows(ResourceNotFoundException.class, () -> service.updateProduct(999, update));
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteProduct_existing_removes() {
        assertDoesNotThrow(() -> service.deleteProduct(2));
        assertThrows(ResourceNotFoundException.class, () -> service.getProductById(2));
    }

    @Test
    void deleteProduct_missing_throwsResourceNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> service.deleteProduct(999));
    }
}
