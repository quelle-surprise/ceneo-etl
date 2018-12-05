package com.etl.etl.service;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public interface DataWarehouseService {

    Iterable<Product> listAllProducts();

    Product getProductById(Integer productId);

    void deleteProduct(Integer productId);

    void deleteAllProducts();

    ResponseEntity<String> extractData(Integer productId) throws Exception;

    ResponseEntity<String> transformData();

    ResponseEntity<String> loadData();


}
