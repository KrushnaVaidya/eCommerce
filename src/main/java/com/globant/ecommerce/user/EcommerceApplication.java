package com.globant.ecommerce.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
@ComponentScan(basePackages = {"com.globant.ecommerce.user"})
@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {	
	ApplicationContext context=SpringApplication.run(EcommerceApplication.class, args);
	
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
