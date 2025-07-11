package com.example.msresident;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsResidentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsResidentApplication.class, args);
	}

}
