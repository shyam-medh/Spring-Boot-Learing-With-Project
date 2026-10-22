package com.devopshub.backend;

import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	static {
		// Enforce UTC timezone before context boots
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Test
	void contextLoads() {
	}

}
