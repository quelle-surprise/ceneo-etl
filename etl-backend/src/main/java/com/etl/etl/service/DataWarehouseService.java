package com.etl.etl.service;

import org.springframework.http.ResponseEntity;

import com.etl.etl.model.entities.Product;

public interface DataWarehouseService {

    Iterable<Product> listAllProducts();

    Product getProductById(Integer productId);

    void deleteProduct(Integer productId);

    void deleteReview(Integer id);

    void deleteAllProducts();

    ResponseEntity<String> extractData(Integer productId) throws Exception;

    ResponseEntity<String> extractDataWithHtml(Integer productId) throws Exception;

    ResponseEntity<Product> transformData();

    ResponseEntity<String> loadData();


}
