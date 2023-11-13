package victor.training.spring.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "show")
public class YamlShowCase {
  Map<DayOfWeek, List<OpeningHours>> timetable;
  Map<String, WarehouseConfig> warehouse;

  @Data
  public static class OpeningHours {
    private String start;
    private String end;
  }
  @Data
  public static class WarehouseConfig {
    private String gps;
    private String owner;
    private OpeningHours hours;
  }

  @PostConstruct
  public void printMe() throws JsonProcessingException {
    System.out.println("Timetable: " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
  }
}
