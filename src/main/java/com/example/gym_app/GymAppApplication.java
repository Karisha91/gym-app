package com.example.gym_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GymAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymAppApplication.class, args);
	}

}
