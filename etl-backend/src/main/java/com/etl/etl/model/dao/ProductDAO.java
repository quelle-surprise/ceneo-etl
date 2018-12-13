package com.etl.etl.model.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import com.etl.etl.model.repository.ProductRepository;


@Component
public class ProductDAO {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Elements extractProductData(Integer productId) throws IOException {
        Document doc = Jsoup.connect("https://www.ceneo.pl/" + productId + "#tab=spec").get();

        return doc.getAllElements();
    }

    public Product transformProductData(Elements extractedProductData) {
        Product product = new Product();
        Set<Review> reviews = new HashSet<>();

        Integer productId = Integer.parseInt(extractedProductData.attr("data-productid"));
        String productName = extractedProductData.select("h1.product-name").text();
        String lowestPrice = extractedProductData.select(".price").attr("content");
        String category = extractedProductData.attr("data-gacategoryname").replaceFirst("/", "");

        product.setProductId(productId);
        product.setProductName(productName);
        product.setLowestPrice(lowestPrice);
        product.setCategory(category);
        product.setReviews(reviews);

        LOG.info(product.toString());
        return product;
    }

    public Product loadProductData(Product transformedProductData, Set<Review> transformedReviewData) {
        for (Review review : transformedReviewData) {
            transformedProductData.getReviews().add(review);
        }
        return productRepository.save(transformedProductData);
    }
}
