package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // sa moara de foame Hikari JDBC Connection Pool
@RequiredArgsConstructor
public class CumSaStarvezConnectionPoolul {
  private final MessageRepo repo;
  @GetMapping("/starve")
  @Transactional
  public String starve() {
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("Altu");
    chemUnRestApi(); // api call in @Transctional care tine conexiunea ocupata
    return "Starving Connection Pool";
  }
  @GetMapping("/iaibanii")
  public String iaibanii() {
    Message contu = repo.findById(1L).orElseThrow();
    return contu.getMessage();
  }

  @SneakyThrows
  private void chemUnRestApi() {
    Thread.sleep(60000);
  }
}
