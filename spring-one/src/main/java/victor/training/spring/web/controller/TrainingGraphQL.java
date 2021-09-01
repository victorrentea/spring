//package victor.training.spring.web.controller;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import graphql.schema.DataFetcher;
//import graphql.schema.DataFetchingEnvironment;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import victor.training.spring.web.domain.Tag;
//import victor.training.spring.web.domain.Training;
//import victor.training.spring.web.repo.TagRepo;
//import victor.training.spring.web.repo.TrainingRepo;
//
//import java.util.List;
//
//import static java.util.Arrays.asList;
//import static java.util.stream.Collectors.toList;
//
//// TODO see http://localhost:8080/graphiql
//
//@Component
//@RequiredArgsConstructor
//public class TrainingGraphQL implements GraphQLQueryResolver {
//   private final TrainingRepo repo;
//
//   public List<Training> allTraining() {
//      return repo.findAll();
//   }
//
//   public Training training(Long id) {
//      return repo.findById(id).get();
//   }
//}
//@Slf4j
//@Component
//@RequiredArgsConstructor
//class TrainingMutationQL implements GraphQLMutationResolver {
//   private final TrainingRepo trainingRepo;
//
//   public Training updateTraining(Long id, String startDate, String name) {
//      log.info("id : " + id + " name: " + name);
//      return null;
//   }
//   public Training updateTrainingDTO(UpdateTrainingDTO input) {
//      log.info("id : " +input);
//      return null;
//   }
//}
//@Data
//class UpdateTrainingDTO{
//   private Long id;
//   private String startDate;
//   private String name;
//}
//
//@RequiredArgsConstructor
//@Component
//class TrainingResolver implements GraphQLResolver<Training> {
//   private final TagRepo tagRepo;
//   public List<String> tagList(Training training) {
//      return tagRepo.findAllByTraining(training.getId()).stream().map(Tag::getName).collect(toList());
//   }
//}
