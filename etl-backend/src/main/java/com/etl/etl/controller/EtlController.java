package com.etl.etl.controller;


import com.etl.etl.model.entities.Product;
import com.etl.etl.model.repository.ProductRepository;
import com.etl.etl.model.repository.ReviewRepository;
import com.etl.etl.service.DataWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/")
public class EtlController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private DataWarehouseService dataWarehouseService;

    /* Mock works with localhost:8080/api/products */
    @RequestMapping(value = "products", method = RequestMethod.GET)
    public List<Product> list() {
        return ProductMock.list();
    }

//    @GetMapping("extract")
//    public ResponseEntity<String> extractData() {
//        return dataWarehouseService.extractData();
//    }
//
//    @GetMapping("transform")
//    public ResponseEntity<String> transformData() {
//        return dataWarehouseService.transformData();
//    }
//
//    @GetMapping("load")
//    public ResponseEntity<String> loadData(){
//        return dataWarehouseService.loadData();
//    }
//
//    @GetMapping("loadFullEtl")
//    public ResponseEntity<String> loadFullEtlProcess () {
//        return dataWarehouseService.loadFullEtlProcess();
//    }
}

