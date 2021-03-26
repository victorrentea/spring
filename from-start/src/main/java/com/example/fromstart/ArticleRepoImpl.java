package com.example.fromstart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ArticleRepoImpl implements ArticleRepoCustom {
   @Autowired
   MongoTemplate mongoTemplate;
   @Override
   public List<Article> search(ArticleSearchCriteria searchCriteria) {
      Query query = new Query();
      query.addCriteria(Criteria.where("name").regex(".*" +searchCriteria.namePart + ".*"));
      return mongoTemplate.find(query, Article.class);
   }
}
