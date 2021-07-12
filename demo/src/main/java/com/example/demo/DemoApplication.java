package com.example.demo;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
//@PropertySource(name = "ceva", value = "c:/opt/fisier1.properties")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}


@Component
class Compo {
	@Value("${fisier.path}")
	private File file;
	@Value("${config2}")
	private int universeAnswer;

	@PostConstruct
	public void readFromFile() throws IOException {
		if (!file.isFile()) {
			throw new IllegalArgumentException("Nu e acolo fisierul asteptat: " + file.getAbsolutePath());
		}
		FileReader reader = new FileReader(file);
		String s = IOUtils.toString(reader);
		System.out.println(s);

		System.out.println(file.getAbsolutePath());
		System.out.println(universeAnswer);
	}

}
