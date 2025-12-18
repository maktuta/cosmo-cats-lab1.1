package org.example.lesson_2.service;

import org.example.lesson_2.dto.ProductDto;
import org.example.lesson_2.exception.ResourceNotFoundException;
import org.example.lesson_2.mapper.ProductMapper;
import org.example.lesson_2.persistence.entity.ProductEntity;
import org.example.lesson_2.persistence.repository.CategoryRepository;
import org.example.lesson_2.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        var category = categoryRepository.findById((long) productDto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + productDto.getCategory() + " not found."));

        ProductEntity entity = mapper.toEntity(productDto);
        entity.setCategory(category);

        ProductEntity saved = productRepository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(int id) {
        ProductEntity product = productRepository.findById((long) id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));
        return mapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(int id, ProductDto updatedDto) {
        ProductEntity existing = productRepository.findById((long) id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));

        var category = categoryRepository.findById((long) updatedDto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + updatedDto.getCategory() + " not found."));

        existing.setProductName(updatedDto.getName());
        existing.setPrice(updatedDto.getPrice() == null ? null : new java.math.BigDecimal(String.valueOf(updatedDto.getPrice())));
        existing.setCategory(category);

        ProductEntity saved = productRepository.save(existing);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        if (!productRepository.existsById((long) id)) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById((long) id);
    }
}
