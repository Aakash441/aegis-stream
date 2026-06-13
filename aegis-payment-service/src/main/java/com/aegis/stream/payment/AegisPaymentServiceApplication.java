package com.aegis.stream.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class AegisPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AegisPaymentServiceApplication.class, args);
	}

}
