package com.example.productsservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.productsservice.entities.Maker;

@Repository
public interface MakerRepository extends CrudRepository<Maker, Long> {
}