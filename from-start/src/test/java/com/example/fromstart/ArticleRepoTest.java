package com.example.fromstart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest// DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class CustomerRepositoryTest {

   @Container
   static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:3.6.23");

   @DynamicPropertySource
   static void setProperties(DynamicPropertyRegistry registry) {
      registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
   }

   @Autowired
   private ArticleRepo articleRepo;

   @AfterEach
   void cleanUp() {
      this.articleRepo.deleteAll();
   }

   @Test
   void shouldReturnListOfCustomerWithMatchingRate() {

      articleRepo.save(new Article("About a Spring Picnic", "Once upon a time...", "green", "fresh", "framework"));

      ArticleSearchCriteria criteria = new ArticleSearchCriteria();
      criteria.namePart = "Spring";
      List<Article> results = articleRepo.search(criteria);

      assertThat(results).hasSize(1);
   }
}