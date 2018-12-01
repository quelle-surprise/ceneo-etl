package com.etl.etl.service;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import com.etl.etl.model.repository.ProductRepository;
import com.etl.etl.model.repository.ReviewRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
    public void loadProductData(Product transformedProductData) {
        productRepository.save(transformedProductData);
        LOG.info("Product has been successfully added to database");
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
    public Elements extractReviewData(Integer productId) throws IOException {
        Elements reviewElements = new Elements();
        Document document = Jsoup.connect("https://www.ceneo.pl/" + productId + "#tab=reviews").get();
        int numberOfReviews = Integer.parseInt(document.select(".product-reviews-numbers span:nth-child(2)").text());
        double numberOfPages = Math.ceil(numberOfReviews/10);
        if(numberOfPages < 1) {
            return document.getAllElements();
        } else {
            for(int i = 1; i<=numberOfPages; i++){
                Document doc = Jsoup.connect("https://www.ceneo.pl/" + productId + "/opinie-" + i).get();
                reviewElements.addAll(doc.select(".show-review-content"));
            }
            return reviewElements;
        }
    }

    @Override
    public ArrayList<Review> transformReviewData(Elements extractedReviewData) {
        ArrayList<Review> reviews = new ArrayList<>();
        for(Element element: extractedReviewData) {
            Review review = new Review();
            String reviewContent = element.select(".product-review-body").text();
            String nameOfReviewer = element.select(".reviewer-name-line").text();
            String reviewScore = element.select(".review-score-count").text();
            review.setReviewContent(reviewContent);
            review.setNameOfReviewer(nameOfReviewer);
            review.setReviewScore(reviewScore);

            reviews.add(review);
        }
        return reviews;
    }

    @Override
    public void loadReviewData(ArrayList<Review> transformedReviewData) {
        for (Review review : transformedReviewData) {
            reviewRepository.save(review);
            LOG.info(review.toString());
        }
    }
}
