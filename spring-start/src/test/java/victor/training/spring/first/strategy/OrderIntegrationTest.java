package victor.training.spring.first.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OrderIntegrationTest.TestConfig.class)
@AutoConfigureMockMvc
@ExtendWith(StdOutCaptureExtension.class)
class OrderIntegrationTest {

  @SpringBootConfiguration
  @EnableAutoConfiguration
  @Import({OrderController.class,
      OrderProcessor.class,
      OnlineOrderHandler.class,
      InStoreOrderHandler.class,
      InternationalOrderHandler.class,
      DefaultOrderHandler.class})
  static class TestConfig {
  }


  @Autowired
  private MockMvc mockMvc;

  private final ObjectMapper jackson = new ObjectMapper();

  @Test
  void whenOnlineOrder_thenOnlineHandlerIsUsed(StdOutCaptureExtension stdOut) throws Exception {
    mockMvc.perform(post("/api/orders/process")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jackson.writeValueAsString(new ProcessOrderRequest().setId("1").setType(OrderType.ONLINE))))
        .andExpect(status().isOk());

    assertThat(stdOut.getCaptured()).contains("OnlineOrderHandler handling");
  }

  @Test
  void whenInStoreOrder_thenInStoreHandlerIsUsed(StdOutCaptureExtension stdOut) throws Exception {
    mockMvc.perform(post("/api/orders/process")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jackson.writeValueAsString(new ProcessOrderRequest().setId("2").setType(OrderType.INSTORE))))
        .andExpect(status().isOk());

    assertThat(stdOut.getCaptured()).contains("InStoreOrderHandler handling");
  }

  @Test
  void whenInternationalOrder_thenInternationalHandlerIsUsed(StdOutCaptureExtension stdOut) throws Exception {
    mockMvc.perform(post("/api/orders/process")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jackson.writeValueAsString(new ProcessOrderRequest().setId("3").setType(OrderType.INTERNATIONAL))))
        .andExpect(status().isOk());

    assertThat(stdOut.getCaptured()).contains("InternationalOrderHandler handling");
  }

  @Test
  void whenUnknownOrder_thenDefaultHandlerIsUsed(StdOutCaptureExtension stdOut) throws Exception {
    mockMvc.perform(post("/api/orders/process")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jackson.writeValueAsString(new ProcessOrderRequest().setId("4").setType(OrderType.UNKNOWN))))
        .andExpect(status().isOk());

    assertThat(stdOut.getCaptured()).contains("DefaultOrderHandler handling");
  }
}
