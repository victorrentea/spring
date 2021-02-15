package com.example.fromscratch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;
import java.util.UUID;

@SpringBootApplication
public class FromScratchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FromScratchApplication.class, args);
	}

	@Value("${in.folder}")
	private File inFolder;

	@PostConstruct
	public void checkFolderPresent() {
		if (!inFolder.isDirectory()) {
			throw new IllegalArgumentException("nu e folder : " + inFolder);
		}
	}

	@Override
	public void run(String... args) throws Exception {
//		applicationContxt.getEnvironment().getProperty("in.folder") nu -  ca face rntime failure
		System.out.println(inFolder);
		System.out.println(inFolder.isDirectory());
	}
}

@Entity
class User {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
