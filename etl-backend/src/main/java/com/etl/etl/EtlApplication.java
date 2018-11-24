package com.etl.etl;

import com.etl.etl.actions.ExtractAction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class EtlApplication {

	public static void main(String[] args) { SpringApplication.run(EtlApplication.class, args);
	}

//	public static void main(String[] args) throws IOException {
//		ExtractAction extractAction = new ExtractAction();
//		extractAction.extractProductData(45002653);
//		extractAction.extractReviewData(45002653);
//	}
}
