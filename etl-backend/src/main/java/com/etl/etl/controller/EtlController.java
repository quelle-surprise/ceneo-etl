package com.etl.etl.controller;


import com.etl.etl.model.entities.Product;
import com.etl.etl.service.DataWarehouseServiceImpl;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class EtlController {

    @Autowired
    private DataWarehouseServiceImpl dataWarehouseServiceImpl;

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public List<Product> list() {
        return ProductMock.list();
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

