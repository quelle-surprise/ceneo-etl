package com.etl.etl.controller;

import com.etl.etl.model.entities.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductMock {
    private static Map<Integer, Product> products = new HashMap<>();

    static {
        Product a = new Product(3213231,"Xbox", "1000.25", "Consoles");
        products.put(1, a);
        Product b = new Product(3213521, "PS4", "1455.25", "Consoles");
        products.put(2, b);
        Product c = new Product(1237313, "Nintendo", "350.25", "Consoles");
        products.put(3, c);
    }
    @GetMapping("/mock")
    public static List<Product> list() {
        return new ArrayList<>(products.values());
    }
}
