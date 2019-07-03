package com.globant.ecommerce.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@ComponentScan(basePackages = {"com.globant.ecommerce.user"})
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
public class EcommerceApplication {

	public static void main(String[] args) {	
	ApplicationContext context=SpringApplication.run(EcommerceApplication.class, args);
	
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
