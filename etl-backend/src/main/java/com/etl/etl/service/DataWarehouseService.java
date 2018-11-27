package com.etl.etl.service;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public interface DataWarehouseService {

    Iterable<Product> listAllProducts();

    Product getProductById(Integer productId);

    Product saveProduct(Product product);

    void deleteProduct(Integer productId);

    Elements extractProductData(Integer productId);

    Product transformProductData(Elements extractProductData);

    Product loadProductData(Product transformedProductData);

    Iterable<Review> listAllReviews();

    Review saveReview(Review review);

    Review getReviewById(Integer id);

    Elements extractReviewData(Integer productId);

    ArrayList<Review> transformReviewData(Elements extractReviewData);

    Review loadReviewData(ArrayList<Review> transformReviewData);




}
