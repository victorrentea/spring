package com.example.fromstart;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Document
public class Article {
   @Id
   private String id = UUID.randomUUID().toString();
   private String name;
   private String description;

   private List<String> tags = new ArrayList<>();

   public Article(String name, String description,String ...tags) {
      this.name = name;
      this.description = description;
      this.tags = asList(tags);
   }
   protected Article() {}

   @Override
   public String toString() {
      return String.format("Article{id='%s', name='%s', description='%s', tags=%s}", id, name, description, tags);
   }
}
