package victor.training.spring.integration.real1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class UglyOne {

  @Autowired
  private EntityManager entityManager;

  @Transactional
//  @EventListener(ApplicationStartedEvent.class)
  public void init() {
    for (int i = 0; i < 10; i++) {
      InMessage inMessage = new InMessage();
      inMessage.setName("Name" + i);
      if (i%2==0)
        inMessage.setTransfers(List.of("Transfer1", "omg1-"+i,"omg2-"+i));
      entityManager.persist(inMessage);
    }
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(10);
  }

  @Bean
  public StandardIntegrationFlow bosch(EntityManagerFactory entityManagerFactory,
                                      FilterOutEmptyRecords filterOutEmptyRecords,
                                      SomeSplitter someSplitter,
                                      SomeTransformer someTransformer,
                                      XYZSubflowConfiguration xyzSubflowConfiguration,
                                       DefaultEventSubFlowConfiguration defaultEventSubFlowConfiguration
                   ) {
    var dbmessagesource = Jpa.inboundAdapter(entityManagerFactory)
            .entityClass(InMessage.class)
            .maxResults(1);
    return IntegrationFlows
            .from(dbmessagesource, c -> c.poller(MyPollerFactory.createSingleThreadedPollerForDataSource("dataSource")))
            .filter(filterOutEmptyRecords)
            .log(Level.INFO, "GOT-FROM-DB")
            .split(someSplitter)
            .channel(MessageChannels.executor(executorService())) //Dispatch to the others threads
            .transform(someTransformer)
            .routeToRecipients(route -> route
//                            .recipientFlow(subflow -> subflow.filter(this::isMultipleOfThree).channel("mutipleOfThreeChannel"))
                            // report events
                            .recipientFlow(xyzSubflowConfiguration.getExpression(), xyzSubflowConfiguration.buildSubFlow())
//                            .recipientFlow(abcSubflowConfiguration.getExpression(), abcSubflowConfiguration.buildSubFlow())
//                            .recipientFlow(defSubflowConfiguration.getExpression(), defSubflowConfiguration.buildSubFlow())
//                            .recipientFlow(anotherSubflowConfiguration.getExpression(), anotherSubflowConfiguration.buildSubFlow())
//                            .recipientFlow(onemoreSubflowConfiiguration.getExpression(), onemoreSubflowConfiiguration.buildSubFlow())
//                            .recipientFlow(encoreConfiguration.getExpression(), encoreConfiguration.buildSubFlow())
//                            .recipientFlow(againSubflowConfiguration.getExpression(),againSubflowConfiguration.buildSubFlow())
//                            //network events
//                            .recipientFlow(grpcNetworkConfiguration.getExpression(), grpcNetworkConfiguration.buildSubFlow())
//                            //some another business events
//                            .recipientFlow(businessSubFlowConfiguration.getExpression(), businessSubFlowConfiguration.buildSubFlow())
//                            // default event
                            .recipientFlow(defaultEventSubFlowConfiguration.getExpression(), defaultEventSubFlowConfiguration.defaultEventSubflow())
            )
            .get();
  }

  private boolean isMultipleOfThree(Integer integer) {
    throw new RuntimeException("Method not implemented");
  }
}

