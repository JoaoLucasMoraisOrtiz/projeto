package com.example.msproprietary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MsProprietaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProprietaryApplication.class, args);
	}

}
