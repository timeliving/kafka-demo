package com.boot.kafka;

import com.boot.kafka.consumer.KafkaConsumerSimple;
import com.boot.kafka.producer.KafkaProducerSimple;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
		String intern = "intern";
		intern.intern();
	}

}
