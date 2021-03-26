package com.example.fromstart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FromStartApplication implements CommandLineRunner {
	private final ArticleRepo articleRepo;

	public FromStartApplication(ArticleRepo articleRepo) {
		this.articleRepo = articleRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(FromStartApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		articleRepo.save(new Article("About a Spring Picnic", "Once upon a time...", "green","fresh","framework"));
//
//		ArticleSearchCriteria criteria = new ArticleSearchCriteria();
//		criteria.namePart = "Spring";
//		System.out.println(articleRepo.search(criteria));
	}
}

