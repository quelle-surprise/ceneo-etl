package com.etl.etl.model.dao;

import com.etl.etl.model.entities.Product;
import com.etl.etl.model.entities.Review;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;



@Component
public class ReviewDAO {

    public Elements extractReviewData(Integer productId) throws IOException {
        Elements reviewElements = new Elements();
        Document document = Jsoup.connect("https://www.ceneo.pl/" + productId + "#tab=reviews").get();
        int numberOfReviews = Integer.parseInt(document.select(".product-reviews-numbers span:nth-child(2)").text());
        int numberOfPages = numberOfReviews/10 + ((numberOfReviews % 10 == 0) ? 0 : 1);
        if(numberOfPages < 1) {
            return document.getAllElements();
        } else {
            for(int i = 1; i<=numberOfPages; i++){
                Document doc = Jsoup.connect("https://www.ceneo.pl/" + productId + "/opinie-"+ i).get();
                reviewElements.addAll(doc.select(".show-review-content"));
            }
        }
        return reviewElements;
    }

    public Set<Review> transformReviewData(Elements extractedReviewData, Product transformedData) {
        Set<Review> reviews = new HashSet<>();
        for(Element element: extractedReviewData) {
            Review review = new Review();
            String reviewContent = element.select(".product-review-body").text();
            String nameOfReviewer = element.select(".reviewer-cell .reviewer-name-line").text(); // TODO: Zwraca puste, trzeba sprawdzic
            String reviewScore = element.select(".review-score-count").text();

            review.setReviewContent(reviewContent);
            review.setNameOfReviewer(nameOfReviewer);
            review.setReviewScore(reviewScore);
            review.setProduct(transformedData);

            reviews.add(review);
        }
        return reviews;
    }
}
