package org.example.lesson_2.mapper;

import org.example.lesson_2.dto.ProductDto;
import org.example.lesson_2.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "productName", target = "name")
    @Mapping(source = "category.id", target = "category")
    @Mapping(source = "id", target = "id")
    @Mapping(expression = "java(product.getPrice() == null ? null : product.getPrice().floatValue())", target = "price")
    ProductDto toDto(ProductEntity product);

    @Mapping(source = "name", target = "productName")
    @Mapping(target = "category", ignore = true) // category is set in service using CategoryRepository
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(expression = "java(dto.getPrice() == null ? null : new java.math.BigDecimal(String.valueOf(dto.getPrice())))", target = "price")
    ProductEntity toEntity(ProductDto dto);
}
