package com.etl.etl.controller;


import java.io.IOException;
import java.util.Set;

import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.etl.etl.model.dao.ProductDAO;
import com.etl.etl.model.dao.ReviewDAO;
import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
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

    @RequestMapping(value = "/etl/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Product> entireEtlProcess(@PathVariable Integer productId) throws IOException {
        if (dataWarehouseServiceImpl.productExist(productId))
            throw new HttpServerErrorException(HttpStatus.CONFLICT, "Product already exist!");
        Elements rawReviewsData = reviewDAO.extractReviewData(productId);
        Elements rawProductData = productDAO.extractProductData(productId);
        Product transformedProductData = productDAO.transformProductData(rawProductData);
        Set<Review> transformedReviewData = reviewDAO.transformReviewData(rawReviewsData, transformedProductData);
        return new ResponseEntity(productDAO.loadProductData(transformedProductData, transformedReviewData), HttpStatus.OK);
    }

    @RequestMapping(value = "/product/delete/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Integer productId) {
        dataWarehouseServiceImpl.deleteProduct(productId);
    }

    @RequestMapping(value = "/review/delete/{id}", method = RequestMethod.DELETE)
    public void deleteReview(@PathVariable Integer id) {
        dataWarehouseServiceImpl.deleteReview(id);
    }

    @RequestMapping(value = "/product/delete", method = RequestMethod.DELETE)
    public void deleteAllProducts() {
        dataWarehouseServiceImpl.deleteAllProducts();
    }

    @RequestMapping(value = "/extract/{productId}", method = RequestMethod.GET)
    public ResponseEntity<String> extractData(@PathVariable Integer productId) throws Exception {
        return dataWarehouseServiceImpl.extractData(productId);
    }

    @RequestMapping(value = "/extract2/{productId}", method = RequestMethod.GET)
    public ResponseEntity<String> extractDataWithHtml(@PathVariable Integer productId) throws Exception {
        return dataWarehouseServiceImpl.extractDataWithHtml(productId);
    }

    @RequestMapping(value = "/transform", method = RequestMethod.GET)
    public ResponseEntity<Product> transformData() {
        return dataWarehouseServiceImpl.transformData();
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ResponseEntity<String> loadData() {
        return dataWarehouseServiceImpl.loadData();
    }

}

