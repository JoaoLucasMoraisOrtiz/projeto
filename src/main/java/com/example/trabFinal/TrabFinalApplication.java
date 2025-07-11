package com.example.trabFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableScheduling
public class TrabFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrabFinalApplication.class, args);
	}

}
