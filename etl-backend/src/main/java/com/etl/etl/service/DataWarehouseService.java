package com.etl.etl.service;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public interface DataWarehouseService {

    Iterable<Product> listAllProducts();

    Product getProductById(Integer productId);

    Product saveProduct(Product product);

    void deleteProduct(Integer productId);

    Elements extractProductData(Integer productId);

    Product transformProductData(Elements extractProductData);

    void loadProductData(Product transformedProductData);

    Iterable<Review> listAllReviews();

    Review saveReview(Review review);

    Review getReviewById(Integer id);

    Elements extractReviewData(Integer productId) throws IOException;

    ArrayList<Review> transformReviewData(Elements extractedReviewData);

    void loadReviewData(ArrayList<Review> transformedReviewData);




}
