package com.pavmaxdav.digital_journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.service.annotation.GetExchange;

@SpringBootApplication
public class DigitalJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalJournalApplication.class, args);
	}
}
