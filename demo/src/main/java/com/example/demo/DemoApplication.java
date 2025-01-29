package com.example.demo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
//@ComponentScan(excludeFilters = @ComponentScan.Filter(ConfigurationProperties.class))
@EnableConfigurationProperties(CatProps.class)
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
@ConfigurationProperties("cat")
@Validated
record CatProps(
		@NotEmpty
		String food,
		boolean water,
		SandType sand
) {
	public enum SandType {
		NISIP, SILICAT
	}
}
@RestController
@RequiredArgsConstructor
class CatApi {
	private final CatService catService;
	@GetMapping("/cats")
	public List<String> getCats() {
		return catService.getCats();
	}
	// #9 Adauga un endpoint POST /cats
	// care sa insereze in DB o noua pisica cu numele "Pride" si rasa "Lion"
	@PostMapping("/cats")
	public void addCat() {
		// TODO
	}
}


@Service
@RequiredArgsConstructor
class CatService{
	private final CatRepository catRepository;
	private final CatProps catProps;
	public List<String> getCats() {
		return catRepository.findAll().stream()
				.map(cat -> cat.getName() + " (" + cat.getBreed() + ")")
				.toList();
	}
	@PostConstruct
	public void init() {
		System.out.println("CatService init with " + catProps);
		catRepository.save(new Cat().setName("Miu-Miu").setBreed("Maidaneza"));
		catRepository.save(new Cat().setName("Tom").setBreed("Siberian"));
		catRepository.save(new Cat().setName("Sara").setBreed("Bengal"));
	}
}

interface CatRepository
		extends JpaRepository<Cat,Long> {}

@Entity
@Data // sau generezi getter+setter+toString
class Cat {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String breed;
}
