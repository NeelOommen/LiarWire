package com.tls.LiarWire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tls")
@EnableCaching
public class LiarWireApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiarWireApplication.class, args);
	}

}
