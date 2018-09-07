package com.sensetime.demo_es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoEsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoEsApplication.class, args);
	}
}
