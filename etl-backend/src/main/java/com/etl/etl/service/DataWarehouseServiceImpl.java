package com.etl.etl.service;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import com.etl.etl.model.repository.ProductRepository;
import com.etl.etl.model.repository.ReviewRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


@Service
public class DataWarehouseServiceImpl implements DataWarehouseService {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    private static final String CENEO_URL = "https://www.ceneo.pl/";
    private static final String TAB_SPEC = "#tab=spec";
    private static final String TAB_SINGLE_REVIEW = "#tab=reviews";
    private static final String TAB_REVIEWS = "/opinie-";
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    Document document = null;

    public DataWarehouseServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getOne(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Elements extractProductData(Integer productId) {
        try {
            document = Jsoup.connect(CENEO_URL + productId + TAB_SPEC).get();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
        return document.getAllElements();
    }

    @Override
    public Product transformProductData(Elements extractProductData) {
        Product product = new Product();

        Integer productId = Integer.parseInt(extractProductData.attr("data-productid"));
        String productName = extractProductData.select("h1.product-name").text();
        String lowestPrice = extractProductData.select(".price").attr("content");
        String category = extractProductData.select(".breadcrumbs .breadcrumb").text();
        //TODO: Improve transforming category

        product.setProductId(productId);
        product.setProductName(productName);
        product.setLowestPrice(lowestPrice);
        product.setCategory(category);

        LOG.info(product.toString());
        return product;
    }

    @Override
    public Product loadProductData(Product transformedProductData) {
        return productRepository.save(transformedProductData);
    }

    @Override
    public Iterable<Review> listAllReviews() {
        return null;
    }

    @Override
    public Review saveReview(Review review) {
        return null;
    }

    @Override
    public Review getReviewById(Integer id) {
        return null;
    }

    @Override
    public Elements extractReviewData(Integer productId) {
        return null;
    }

    @Override
    public ArrayList<Review> transformReviewData(Elements extractReviewData) {
        return null;
    }

    @Override
    public Review loadReviewData(ArrayList<Review> transformReviewData) {
        return null;
    }
}
