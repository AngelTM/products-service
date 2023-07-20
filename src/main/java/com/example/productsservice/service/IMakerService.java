package com.example.productsservice.service;

import java.util.List;
import java.util.Optional;

import com.example.productsservice.entities.Maker;

public interface IMakerService {
    List<Maker> findAll();

    Optional<Maker> findById(Long id);

    void save(Maker maker);

    void deleteById(Long id);
}
