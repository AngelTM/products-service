package com.example.productsservice.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.productsservice.entities.Product;

public interface IProductDAO {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    void save(Product product);

    void deleteById(Long id);
}
