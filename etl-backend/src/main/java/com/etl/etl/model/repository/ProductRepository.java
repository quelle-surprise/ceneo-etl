package com.etl.etl.model.repository;

import com.etl.etl.model.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
    //TODO: Implement queries for Products
}
