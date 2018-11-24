package com.etl.etl.actions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;



import java.io.IOException;


public class ExtractAction{

    public Elements extractProductData(Integer productId) throws IOException {
        Document document = Jsoup.connect("https://www.ceneo.pl/" + productId + "#tab=spec").get();
        return document.getAllElements();
    }

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

}
