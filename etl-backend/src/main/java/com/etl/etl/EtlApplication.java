package com.etl.etl;

;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EtlApplication {

	public static void main(String[] args) { SpringApplication.run(EtlApplication.class, args);
	}

//	public static void main(String[] args) throws IOException {
//		ExtractAction extractAction = new ExtractAction();
//
//		Elements listOfReviews = extractAction.extractReviewData(45002653);
//		Elements listOfProducts = extractAction.extractProductData(45002653);
//		TransformAction transformAction = new TransformAction(listOfProducts, listOfReviews);
//
////		transformAction.transformReviewPages(listOfReviews);
//		transformAction.transformProductPage(listOfProducts);
//
//	}
}
