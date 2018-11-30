package com.etl.etl.controller;


import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.etl.etl.model.entities.Product;
import com.etl.etl.service.DataWarehouseServiceImpl;

@RestController
@CrossOrigin
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

    @RequestMapping(value = "product/{productId}", method = RequestMethod.POST)
    public Product entireEtlProcess(@PathVariable Integer productId) {
        Elements rawProductData = dataWarehouseServiceImpl.extractProductData(productId);
        Product transformedProductData = dataWarehouseServiceImpl.transformProductData(rawProductData);
        return dataWarehouseServiceImpl.loadProductData(transformedProductData);
    }
}

