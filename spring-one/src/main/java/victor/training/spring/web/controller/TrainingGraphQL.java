package victor.training.spring.web.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Tag;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TagRepo;
import victor.training.spring.web.repo.TrainingRepo;

import java.util.List;

import static java.util.stream.Collectors.toList;

// TODO see http://localhost:8080/graphiql

@Component
@RequiredArgsConstructor
public class TrainingGraphQL implements GraphQLQueryResolver {
   private final TrainingRepo repo;

   public List<Training> allTraining() {
      return repo.findAll();
   }

   public Training training(Long id) {
      return repo.findById(id).get();
   }
}

@Component
@RequiredArgsConstructor
class TrainingDataFetcher implements DataFetcher<Training> {
   private final TrainingRepo repo;

   @Override
   public Training get(DataFetchingEnvironment environment) {
      Integer id = environment.getArgument("id");
      return repo.findById(id.longValue()).get();
   }
}
