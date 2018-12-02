package com.etl.etl.controller;



import com.etl.etl.model.entities.Review;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public Iterable<Product> listAllProducts() {
        return dataWarehouseServiceImpl.listAllProducts();
    }

    @GetMapping(value = "product/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return dataWarehouseServiceImpl.getProductById(productId);
    }

    //TODO : To improve
    @RequestMapping(value = "product/{productId}", method = RequestMethod.POST)
    public Product entireEtlProcess(@PathVariable Integer productId) throws IOException {
        Elements rawReviewsData = dataWarehouseServiceImpl.extractReviewData(productId);
        Elements rawProductData = dataWarehouseServiceImpl.extractProductData(productId);
        Product transformedProductData = dataWarehouseServiceImpl.transformData(rawProductData, rawReviewsData);
        Set<Review> transformedReviewData = dataWarehouseServiceImpl.transformReviewData(rawReviewsData, transformedProductData);
        return dataWarehouseServiceImpl.loadProductData(transformedProductData, transformedReviewData);
    }

    @RequestMapping(value = "product/delete/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Integer productId) {
        dataWarehouseServiceImpl.deleteProduct(productId);
    }

    @RequestMapping(value = "product/delete", method = RequestMethod.DELETE)
    public void deleteAllProducts() {
        dataWarehouseServiceImpl.deleteAllProducts();
    }
}

