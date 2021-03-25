package com.example.fromstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@SpringBootApplication
public class FromStartApplication {
	public static void main(String[] args) {
		SpringApplication.run(FromStartApplication.class, args);
	}

//	@Bean
//	public void validator() {
//	    return ValidatorFactory.getValidator();
//	}

	@Validated
	public void method(@NotNull Long id, @Valid SomeData data) {

	}
}
class SomeData {
	@NotNull
	Long id;
}

