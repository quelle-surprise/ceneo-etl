package com.etl.etl.service;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public interface DataWarehouseService {

    Iterable<Product> listAllProducts();

    Product getProductById(Integer productId);

    void deleteProduct(Integer productId);

    Elements extractProductData(Integer productId);

    Product transformData(Elements extractedProductData, Elements extractedReviewData);

    Product loadProductData(Product transformedProductData, Set<Review> transformedReviewData);

    Elements extractReviewData(Integer productId) throws IOException;

    Set<Review> transformReviewData(Elements extractedReviewData, Product transformData);

    void deleteAllProducts();
}
