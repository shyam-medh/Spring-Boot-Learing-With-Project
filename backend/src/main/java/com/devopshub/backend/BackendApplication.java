package com.devopshub.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class BackendApplication {

	static {
		// Enforce UTC globally so PostgreSQL never rejects Windows "Asia/Calcutta" in main OR test executions
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
