package com.vf.eventhubserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ComponentScan(basePackages = "com.vf.eventhubserver.configuration")
public class EventhubServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventhubServerApplication.class, args);
	}

}
