package com.etl.etl.controller;



import com.etl.etl.model.dao.ProductDAO;
import com.etl.etl.model.dao.ReviewDAO;
import com.etl.etl.model.entities.Review;
import com.etl.etl.service.DataWarehouseService;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import com.etl.etl.model.entities.Product;
import com.etl.etl.service.DataWarehouseServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("api")
public class EtlController {

    @Autowired
    private DataWarehouseServiceImpl dataWarehouseServiceImpl;
    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    ProductDAO productDAO;

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public Iterable<Product> listAllProducts() {
        return dataWarehouseServiceImpl.listAllProducts();
    }

    @GetMapping(value = "product/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return dataWarehouseServiceImpl.getProductById(productId);
    }

    //TODO : To improve
    @RequestMapping(value = "/etl/{productId}", method = RequestMethod.POST)
    public Product entireEtlProcess(@PathVariable Integer productId) throws IOException {
        Elements rawReviewsData = reviewDAO.extractReviewData(productId);
        Elements rawProductData = productDAO.extractProductData(productId);
        Product transformedProductData = productDAO.transformProductData(rawProductData);
        Set<Review> transformedReviewData = reviewDAO.transformReviewData(rawReviewsData, transformedProductData);
        return productDAO.loadProductData(transformedProductData, transformedReviewData);
    }

    @RequestMapping(value = "/product/delete/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Integer productId) {
        dataWarehouseServiceImpl.deleteProduct(productId);
    }

    @RequestMapping(value = "/product/delete", method = RequestMethod.DELETE)
    public void deleteAllProducts() {
        dataWarehouseServiceImpl.deleteAllProducts();
    }

    @RequestMapping(value = "/extract/{productId}", method = RequestMethod.GET)
    public ResponseEntity<String> extractData(@PathVariable Integer productId) throws Exception {
        return dataWarehouseServiceImpl.extractData(productId);
    }

    @RequestMapping(value = "/transform", method = RequestMethod.GET)
    public ResponseEntity<String> transformData() {
        return dataWarehouseServiceImpl.transformData();
    }

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public ResponseEntity<String> loadData() {
        return dataWarehouseServiceImpl.loadData();
    }

}

