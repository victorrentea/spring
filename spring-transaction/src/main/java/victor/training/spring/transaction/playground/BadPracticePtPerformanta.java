package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class BadPracticePtPerformanta {
  private final MessageRepo messageRepo;

  public void apelInDB1() { // nu e nevoie de tranzactie, pt ca save e @Transactional
    messageRepo.save(new Message("Start"));
  }
  public void apelInDB2() { //ai nevoie
    messageRepo.save(new Message("1"));
    messageRepo.save(new Message("2"));
  }
  public void apiCallLung10sec() {
    String result = new RestTemplate().getForObject("http://localhost:8080/api", String.class);
    // potential terrible performance hit pt ca blocheaza tranzactia pt 10 secunde
    // cate tranzactii am default maxim in JDBC Connection Pool ? 10
    // poti avea JDBC COnnection Pool starvation -> sevede pe metrici usor
    // hikari.connection.acquisition_time , gen..
  }
}
