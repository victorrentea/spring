package victor.training.spring.web.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import graphql.schema.idl.TypeRuntimeWiring.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TagRepo;
import victor.training.spring.web.repo.TrainingRepo;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trainings/graphql")
public class TrainingGraphQL {
   private final TrainingGraphQLService graphQLService;
   @PostMapping
   public ExecutionResult search(@RequestBody String query) {
      return graphQLService.getGraphQL().execute(query);
   }
}


@RequiredArgsConstructor
@Service
class TrainingGraphQLService {
   private final TrainingDataFetcher trainingDataFetcher;
   private final AllTrainingDataFetcher allTrainingDataFetcher;

   @Value("classpath:training.graphql")
   private Resource resource;
   private GraphQL graphQL;

   @PostConstruct
   public void loadSchema() throws IOException {
      File file = resource.getFile();
      TypeDefinitionRegistry registry = new SchemaParser().parse(file);
      RuntimeWiring wiring = buildRuntimeWiring();
      GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
      graphQL = GraphQL.newGraphQL(schema).build();
   }

   private RuntimeWiring buildRuntimeWiring() {
      return RuntimeWiring.newRuntimeWiring()
            .type("Query", typeWiring ->
                typeWiring
                    .dataFetcher("allTraining", allTrainingDataFetcher)
                    .dataFetcher("training", trainingDataFetcher))
          .build();
   }

   public GraphQL getGraphQL() {
      return graphQL;
   }
}


//@Component
//@RequiredArgsConstructor
//class TrainingQuery implements GraphQLQueryResolver {
//   private final TrainingRepo repo;
//
//   public List<Training> allTraining() {
//      return repo.findAll();
//   }
//}

@Component
@RequiredArgsConstructor
class TrainingDataFetcher implements DataFetcher<Training>{
   private final TrainingRepo repo;
   @Override
   public Training get(DataFetchingEnvironment environment) {
      Integer id = environment.getArgument("id");
      return repo.findById(id.longValue()).get();
   }
}

@RequiredArgsConstructor
@Component
class TrainingResolver implements GraphQLResolver<Training> {
   private final TagRepo tagRepo;
   public List<String> tagList(Training training) {
      return asList("a","b");
   }
}

@Component
@RequiredArgsConstructor
class AllTrainingDataFetcher implements DataFetcher<List<Training>> {
   private final TrainingRepo repo;
   @Override
   public List<Training> get(DataFetchingEnvironment environment) {
      return repo.findAll();
   }
}