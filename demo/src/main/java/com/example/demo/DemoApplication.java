package com.example.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@EnableAsync
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public S s() {
	    return new S();
	}
}

class S {

}


@Data
@Component
@ConfigurationProperties(prefix = "welcome")
class WelcomeConfig {
	private String welcomeMessage;
//	private int hostPort;
	private List<String> supportUrls;
	private Map<String, String> localContactPhone;
	private HelpInfo help;
	@Data
	static class HelpInfo {
		private URL helpUrl;
		private String iconUri;
	}
}


@RestController
@RequiredArgsConstructor
class PrimuRest {
	private final WelcomeConfig config;

	@Value("${welcome.welcomeMessage}")
	private String message;

//	@Value("${PATH}")
//	private String string;

	@GetMapping("hello")
	public String method() {
	    return config.toString();
	}
//	@GetMapping("path")
//	public String path() {
//	    return string;
//	}

	@PostMapping("api/salut")
	public String salut(@RequestBody SalutDto dto) {
		return dto.salut.toUpperCase();
	}

	@PreAuthorize("hasRole('ADMIN')")
//	@PreAuthorize("hasAuthority('areVoieSaLansezeFocoasele')")
	@GetMapping("admin/prapad")
	public String prapad() {
		return "Le-am lansat";
	}

	@Autowired
	private FileProcessor processor;

	@PostMapping("upload")
	public String upload(@RequestParam("fisier") MultipartFile file) throws IOException {
		File tempFile = File.createTempFile("test", "tmp");

		try (FileOutputStream tempOutputStream = new FileOutputStream(tempFile)) {
			IOUtils.copy(file.getInputStream(), tempOutputStream);
		}
		// TODO faci ce ai de facut cu el: 1) il pui in DB 2) il trimiti pe SCP 3) il trimi catre alt API cu http request, 4) cozi 5) Il procesezi linie cu linie

//		File stageFolder = new File("C:\\workspace\\spring\\demo\\stage");
//		File toProcess = new File(stageFolder, file.getOriginalFilename());
//
//		Files.move(tempFile.toPath(), toProcess.toPath());

		processor.rasneste(tempFile);

		return "Primit! E in procesare. " + file.getOriginalFilename() ;
	}
}

@Component
class FileProcessor {
	@SneakyThrows
	@Async
	public void rasneste(File file) {
		try {
			// logica multa de parsare

			Thread.sleep(5000);
			File dest= new File("C:\\workspace\\spring\\demo\\up.jpg");
			Files.copy(file.toPath(), dest.toPath());
			System.out.println("Gata");
		} finally {
			file.delete();
		}
	}
}

class SalutDto {
	public String salut;
}

