package org.example.lesson_2.mapper;

import org.example.lesson_2.domain.Product;
import org.example.lesson_2.dto.ProductDto;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);
    Product toEntity(ProductDto dto);
}
