package com.etl.etl.actions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.logging.Logger;


public class ExtractAction{
    private final Logger LOG = Logger.getLogger(getClass().getName());
    private static final String CENEO_URL = "https://www.ceneo.pl/";
    private static final String TAB_SPEC = "#tab=spec";
    private static final String TAB_SINGLE_REVIEW = "#tab=spec";
    private static final String TAB_REVIEWS = "/opinie-";

    public ExtractAction() {

    }

    public Elements extractProductData(Integer productId) throws IOException {
        Document document = Jsoup.connect(CENEO_URL + productId + TAB_SPEC).get();
        return document.getAllElements();
    }

    public Elements extractReviewData(Integer productId) throws IOException {
        Elements reviewElements = new Elements();
        Document document = Jsoup.connect(CENEO_URL + productId + TAB_SINGLE_REVIEW).get();
        int numberOfReviews = Integer.parseInt(document.select(".product-reviews-numbers span:nth-child(2)").text());
        double numberOfPages = Math.ceil(numberOfReviews/10.0);
        if(numberOfPages < 1) {
            return document.select(".show-review-content");
        } else {
            for(int i = 1; i<=numberOfPages; i++){
                Document doc = Jsoup.connect(CENEO_URL + productId + TAB_REVIEWS + i).get();
                reviewElements.addAll(doc.select(".review-box"));
            }
            LOG.info("" + reviewElements.size());
            return reviewElements;
        }
    }
}
