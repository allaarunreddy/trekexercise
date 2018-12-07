package com.bikes.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bikes.service.BikeService;
import com.bikes.service.BikeServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	public BikeService getSampleService() {
		return new BikeServiceImpl();
	}

}
