package com.etl.etl.controller;

import com.etl.etl.model.entities.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductMock {
    private static Map<Integer, Product> products = new HashMap<>();

    static {
        Product a = new Product(1, 321321, "PS4", 1455.25, "Consoles", "Some informations");
        products.put(1, a);
        Product b = new Product(2, 321321, "PS4", 1455.25, "Consoles", "Some informations");
        products.put(2, b);
        Product c = new Product(3, 321321, "PS4", 1455.25, "Consoles", "Some informations");
        products.put(3, c);
    }

    public static List<Product> list() {
        return new ArrayList<>(products.values());
    }
}
