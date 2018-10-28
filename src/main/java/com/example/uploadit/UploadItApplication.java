package com.example.uploadit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class UploadItApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadItApplication.class, args);
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		return new CommonsMultipartResolver();
	}
}
