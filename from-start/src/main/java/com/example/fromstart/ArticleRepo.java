package com.example.fromstart;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepo extends MongoRepository<Article, String>, ArticleRepoCustom {
}
