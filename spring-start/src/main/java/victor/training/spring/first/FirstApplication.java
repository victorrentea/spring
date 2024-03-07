package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import victor.training.spring.first.async.BarApi;
import victor.training.spring.first.async.BarmanService;
import victor.training.spring.first.events.inventory.InvoiceService;
import victor.training.spring.first.events.invoicing.StockManagementService;
import victor.training.spring.first.events.order.OrderService;

@SpringBootApplication
@ComponentScan(basePackages = {"com.picnic.myapp"})
@EnableConfigurationProperties(Props.class)
@Import(value = {
    RestApi.class,
    GlobalExceptionHandler.class,
    Repo.class,
    PropsConfig.class,
    MyConfig.class,
    RestFTW.class,
    InvoiceService.class,
    StockManagementService.class,
    ScheduledPlay.class,
    OrderService.class,
    BarApi.class,
    BarmanService.class,
    // don't let this list discourage you from extracting a new class from an existing
    // larger one.

    MailServiceImpl.class, MailServiceDummy.class})
@EnableScheduling
@EnableAsync
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }
  {
//    Proxy
  }
  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    System.out.println(x.logic());
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    System.out.println("App started OK 🎉");
  }
}

