package com.example.fromscratch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties("in")
class Configuratie {

	private File folder;
	private long rateMillis;
	private List<String> extensions;

	private Map<String, String> hello;

	@PostConstruct
	public void checkFolderPresent() {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("nu e folder : " + folder);
		}
		System.out.println(extensions);
		System.out.println(folder);
		System.out.println(hello);
		System.out.println(folder.isDirectory());
	}

	public File getFolder() {
		return folder;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}

	public long getRateMillis() {
		return rateMillis;
	}

	public void setRateMillis(long rateMillis) {
		this.rateMillis = rateMillis;
	}

	public List<String> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<String> extensions) {
		this.extensions = extensions;
	}

	public Map<String, String> getHello() {
		return hello;
	}

	public void setHello(Map<String, String> hello) {
		this.hello = hello;
	}
}

@EnableConfigurationProperties
@SpringBootApplication
public class FromScratchApplication  {

	public static void main(String[] args) {
		SpringApplication.run(FromScratchApplication.class, args);
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
