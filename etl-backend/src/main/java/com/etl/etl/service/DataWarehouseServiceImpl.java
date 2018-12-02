package com.etl.etl.service;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import com.etl.etl.model.repository.ProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


@Service
public class DataWarehouseServiceImpl implements DataWarehouseService {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    private static final String CENEO_URL = "https://www.ceneo.pl/";
    private static final String TAB_SPEC = "#tab=spec";
    private static final String TAB_SINGLE_REVIEW = "#tab=reviews";
    private static final String TAB_REVIEWS = "/opinie-";
    private final ProductRepository productRepository;
    Document document = null;

    public DataWarehouseServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public Product transformData(Elements extractedProductData, Elements extractedReviewData) {
        Product product = new Product();
        Set<Review> reviews = new HashSet<>();

        Integer productId = Integer.parseInt(extractedProductData.attr("data-productid"));
        String productName = extractedProductData.select("h1.product-name").text();
        String lowestPrice = extractedProductData.select(".price").attr("content");
        String category = extractedProductData.select(".breadcrumbs .breadcrumb").text();
        //TODO: Improve transforming category

        product.setProductId(productId);
        product.setProductName(productName);
        product.setLowestPrice(lowestPrice);
        product.setCategory(category);
        product.setReviews(reviews);

        LOG.info(product.toString());
        return product;
    }

    @Override
    public Product loadProductData(Product transformedProductData, Set<Review> transformedReviewData) {
        for(Review review: transformedReviewData) {
            transformedProductData.getReviews().add(review);
        }
        return productRepository.save(transformedProductData);
    }

    @Override
    public Elements extractReviewData(Integer productId) throws IOException {
        Elements reviewElements = new Elements();
        Document document = Jsoup.connect(CENEO_URL + productId + TAB_SINGLE_REVIEW).get();
        int numberOfReviews = Integer.parseInt(document.select(".product-reviews-numbers span:nth-child(2)").text());
        int numberOfPages = numberOfReviews/10 + ((numberOfReviews % 10 == 0) ? 0 : 1);
        if(numberOfPages < 1) {
            return document.getAllElements();
        } else {
            for(int i = 1; i<=numberOfPages; i++){
                Document doc = Jsoup.connect(CENEO_URL + productId + TAB_REVIEWS + i).get();
                reviewElements.addAll(doc.select(".show-review-content"));
            }
        }
        return reviewElements;
    }

    @Override
    public Set<Review> transformReviewData(Elements extractedReviewData, Product transformData) {
        Set<Review> reviews = new HashSet<>();
        for(Element element: extractedReviewData) {
            Review review = new Review();
            String reviewContent = element.select(".product-review-body").text();
            String nameOfReviewer = element.select(".reviewer-cell .reviewer-name-line").text();
            String reviewScore = element.select(".review-score-count").text();

            review.setReviewContent(reviewContent);
            review.setNameOfReviewer(nameOfReviewer);
            review.setReviewScore(reviewScore);
            review.setProduct(transformData);

            reviews.add(review);
        }
        return reviews;
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
