package com.etl.etl.actions;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Data;

import java.util.ArrayList;
import java.util.logging.Logger;


public class TransformAction {
    protected final Logger LOG = Logger.getLogger(getClass().getName());
    private ArrayList<Review> reviews = new ArrayList<>();

    private Elements rawProductPages;
    private Elements rawReviewPages;

    public TransformAction(Elements rawProductPages, Elements rawReviewPages) {
        this.rawProductPages = rawProductPages;
        this.rawReviewPages = rawReviewPages;
    }

    public ArrayList<Review> transformReviewPages(Elements rawReviewPages) {
        for(Element element: rawReviewPages) {
            Review review = new Review();

            String reviewContent = element.select(".product-review-body").text();
            String nameOfReviewer = element.select(".reviewer-name-line").text();
            String reviewScore = element.select(".review-score-count").text();
            Integer productId = Integer.parseInt(element.baseUri().substring(21, 29));

            review.setReviewContent(reviewContent);
            review.setNameOfReviewer(nameOfReviewer);
            review.setReviewScore(reviewScore);
            review.setProductId(productId);
            LOG.info("" + productId);

            reviews.add(review);
        }
        return reviews;
    }

    public Product transformProductPage(Elements rawProductPage) {
        Product product = new Product();

        Integer productId = Integer.parseInt(rawProductPage.attr("data-productid"));
        String productName = rawProductPage.select("h1.product-name").text();
        String lowestPrice = rawProductPage.select(".price").attr("content");
        String category = rawProductPage.select(".breadcrumbs .breadcrumb").text();
        //TODO: Improve transforming category

        product.setProductId(productId);
        product.setProductName(productName);
        product.setLowestPrice(lowestPrice);
        product.setCategory(category);

        LOG.info(product.toString());
        return product;
    }
}
