package com.etl.etl.controller;


import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import com.etl.etl.service.DataWarehouseServiceImpl;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("api")
public class EtlController {

    @Autowired
    private DataWarehouseServiceImpl dataWarehouseServiceImpl;

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public Iterable<Product> list() {
        return dataWarehouseServiceImpl.listAllProducts();
    }

    @GetMapping(value = "products/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return dataWarehouseServiceImpl.getProductById(productId);
    }

    //TODO : To improve
    @RequestMapping(value = "product/{productId}", method = RequestMethod.POST)
    public void entireEtlProcess(@PathVariable Integer productId) throws IOException, InterruptedException {
        Elements rawProductData = dataWarehouseServiceImpl.extractProductData(productId);
        Product transformedProductData = dataWarehouseServiceImpl.transformProductData(rawProductData);
        dataWarehouseServiceImpl.loadProductData(transformedProductData);
        Elements rawReviewsData = dataWarehouseServiceImpl.extractReviewData(productId);
        ArrayList<Review> transformedReviewData = dataWarehouseServiceImpl.transformReviewData(rawReviewsData);
        dataWarehouseServiceImpl.loadReviewData(transformedReviewData);
    }
}

