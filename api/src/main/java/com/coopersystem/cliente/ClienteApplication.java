package com.coopersystem.cliente;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * 
 * @author gekson
 *
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { 
		ClienteApplication.class,
		Jsr310JpaConverters.class 
})
public class ClienteApplication {
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
	}

}
