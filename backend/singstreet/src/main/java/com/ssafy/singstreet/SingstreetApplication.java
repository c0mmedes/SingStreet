package com.ssafy.singstreet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SingstreetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SingstreetApplication.class, args);
	}

}
