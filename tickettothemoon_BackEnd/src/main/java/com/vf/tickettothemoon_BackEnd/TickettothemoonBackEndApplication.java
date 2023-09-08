package com.vf.tickettothemoon_BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// @ComponentScan(basePackages = "com.vf.tickettothemoon_BackEnd.configuration")
public class TickettothemoonBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickettothemoonBackEndApplication.class, args);
	}

}
