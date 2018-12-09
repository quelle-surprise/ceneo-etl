package com.etl.etl.service;

import com.etl.etl.model.dao.ProductDAO;
import com.etl.etl.model.dao.ReviewDAO;
import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import com.etl.etl.model.repository.ProductRepository;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpServerErrorException;


import java.util.Set;
import java.util.logging.Logger;


@Service
public class DataWarehouseServiceImpl implements DataWarehouseService {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    private final ProductRepository productRepository;
    private Elements extractedProductData = null;
    private Elements extractedReviewData = null;
    private Product transformedProductData = null;
    private Set<Review> transformedReviewData = null;

    @Autowired
    ProductDAO productDAO;
    @Autowired
    ReviewDAO reviewDAO;

    public DataWarehouseServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getOne(id);
    }

    public boolean productExist(Integer id) {
        return productRepository.existsById(id);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Override
    public ResponseEntity<String> extractData(Integer productId) throws Exception {
        if (productExist(productId)) throw new HttpServerErrorException(HttpStatus.CONFLICT, "Product already exist!");

        extractedProductData = null;
        extractedReviewData = null;
        transformedProductData = null;
        transformedReviewData = null;

        extractedProductData = productDAO.extractProductData(productId);
        extractedReviewData = reviewDAO.extractReviewData(productId);

        return new ResponseEntity<>("Data has been extracted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> extractDataWithHtml(Integer productId) throws Exception {
        if (productExist(productId)) throw new HttpServerErrorException(HttpStatus.CONFLICT, "Product already exist!");

        extractedProductData = null;
        extractedReviewData = null;
        transformedProductData = null;
        transformedReviewData = null;

        extractedProductData = productDAO.extractProductData(productId);
        extractedReviewData = reviewDAO.extractReviewData(productId);

        return new ResponseEntity<>("Data has been extracted successfully\nExtracted product data: " + extractedProductData + "Extracted review data: \n" + extractedReviewData, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> transformData() {
        LOG.info("Extracted product data: " + extractedProductData);
        LOG.info("Extracted review data: " + extractedReviewData);
        if (extractedReviewData != null && extractedProductData != null) {
            transformedProductData = productDAO.transformProductData(extractedProductData);
            if (transformedProductData == null) {
                throw new HttpServerErrorException(HttpStatus.CONFLICT, "Error happens while transforming data");
            }
            transformedReviewData = reviewDAO.transformReviewData(extractedReviewData, transformedProductData);

        } else {
            throw new HttpServerErrorException(HttpStatus.CONFLICT, "It looks that there is no data to extract!");
        }
//        return new ResponseEntity<>("Data has been transformed successfully\n Transformed Product Data: " + transformedProductData.toString()
//                + "" +
//                "\n Transformed Review Data: " + transformedReviewData.toString(), HttpStatus.OK);
        transformedProductData.setReviews(transformedReviewData);
        return new ResponseEntity<>(transformedProductData, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<String> loadData() {
        LOG.info("Transformed product data:" + transformedProductData);
        LOG.info("Transformed review data" + transformedReviewData);
        if (transformedReviewData != null && transformedProductData != null) {
            productDAO.loadProductData(transformedProductData, transformedReviewData);
        } else {
            return new ResponseEntity<>("Error happens while loading data", HttpStatus.CONFLICT);
        }
        extractedProductData = null;
        extractedReviewData = null;
        transformedProductData = null;
        transformedReviewData = null;
        return new ResponseEntity<>("Data Loaded successfully", HttpStatus.OK);
    }

}
