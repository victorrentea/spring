package victor.training.spring.kafka;

import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

// a real Kafka broker in this JVM, no Docker needed. Stop it = stop this program (all messages are lost)
public class StartKafkaBroker {
  public static void main(String[] args) throws InterruptedException {
    // port 9093, not Kafka's usual 9092: StartDatabase (H2) listens there
    EmbeddedKafkaBroker broker = new EmbeddedKafkaKraftBroker(1, 1, "demo")
            .brokerProperty("listeners", "EXTERNAL://localhost:9093,CONTROLLER://localhost:0");
    broker.afterPropertiesSet();
    System.out.println("Kafka broker started on " + broker.getBrokersAsString() + " with topic 'demo'");
    Thread.currentThread().join(); // keep running until stopped
  }
}
