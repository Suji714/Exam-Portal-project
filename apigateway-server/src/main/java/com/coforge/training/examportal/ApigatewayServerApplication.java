package com.coforge.training.examportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApigatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayServerApplication.class, args);
	}

}
