package victor.training.spring.ai.other;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

;

@Service
public class WeatherClient {
  private static final String BASE_URL = "https://api.weather.gov";

  private final RestClient restClient;

  public WeatherClient() {

    this.restClient = RestClient.builder()
        .baseUrl(BASE_URL)
        .defaultHeader("Accept", "application/geo+json")
        .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
        .build();
  }

  public record Points(Props properties) {
    public record Props(String forecast) {
    }
  }

  public record Forecast(Props properties) {
    public record Props(List<Period> periods) {
    }
    public record Period(
        Integer number, String name,
        String startTime, String endTime,
        Boolean isDayTime, Integer temperature,
        String temperatureUnit,
        String temperatureTrend,
        Map probabilityOfPrecipitation,
        String windSpeed, String windDirection,
        String icon, String shortForecast,
        String detailedForecast) {
    }
  }

  public record Alert(List<Feature> features) {

    public record Feature(Properties properties) {
    }

    public record Properties(String event, String areaDesc,
                             String severity, String description,
                             String instruction) {
    }
  }



  public Forecast getForecast(double latitude, double longitude) {
    var points = restClient.get()
        .uri("/points/{latitude},{longitude}", latitude, longitude)
        .retrieve()
        .body(Points.class);

    return restClient.get().uri(points.properties().forecast()).retrieve().body(Forecast.class);
  }

  public Alert getAlert(String state) {
    return restClient.get().uri("/alerts/active/area/{state}", state).retrieve().body(Alert.class);
  }
  public record WeatherResponse(Current current) {
    public record Current(LocalDateTime time, int interval, double temperature_2m) {}
  }

  public WeatherResponse getWeather(double latitude, double longitude) {
    return RestClient.create()
        .get()
        .uri("https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m",
            latitude, longitude)
        .retrieve()
        .body(WeatherResponse.class);
  }
}
