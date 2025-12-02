package victor.training.spring.ai;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpProgressToken;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class WeatherMCP {


  private static final Logger log = LoggerFactory.getLogger(WeatherMCP.class);
  private final WeatherClient weatherClient;

  public WeatherMCP(WeatherClient weatherClient) {
    this.weatherClient = weatherClient;
  }

//	@Tool(description = "Get weather forecast for a specific latitude/longitude")
	public String getWeatherForecastByLocation(double latitude, double longitude) {

    WeatherClient.Forecast forecast = weatherClient.getForecast(latitude,longitude);

		String forecastText = forecast.properties().periods().stream().map(p -> {
			return String.format("""
					%s:
					Temperature: %s %s
					Wind: %s %s
					Forecast: %s
					""", p.name(), p.temperature(), p.temperatureUnit(), p.windSpeed(), p.windDirection(),
					p.detailedForecast());
		}).collect(Collectors.joining());

		return forecastText;
	}

	@Tool(description = "Get weather forecast for a specific latitude/longitude in a poetic form")
	public String getPoeticWeatherForecastByLocation(double latitude, double longitude,
                                                   ToolContext toolContext,
                                                   @McpProgressToken String progressToken) {

    log.info("Start");
    Optional<McpSyncServerExchange> exchangeOpt =McpToolUtils.getMcpExchange(toolContext);
    if (exchangeOpt.isEmpty()) {
      return "Not poetic";
    };
    McpSyncServerExchange exchange = exchangeOpt.get();

    log.info("One");
    exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder() // (3)
        .level(McpSchema.LoggingLevel.DEBUG)
        .data("Call getWeather VVV Tool with latitude: " + latitude + " and longitude: " + longitude)
        .meta(Map.of()) // non null meta as a workaround for bug: ...
        .build());


    WeatherClient.WeatherResponse weatherResponse = weatherClient.getWeather(latitude, longitude);
    log.info("Two: "+weatherResponse);

    String epicPoem = "MCP Client doesn't provide sampling capability.";

    if (exchange.getClientCapabilities().sampling() != null) {
      // 50% progress
      exchange.progressNotification(new McpSchema.ProgressNotification(progressToken, 0.5, 1.0, "Start sampling"));	// (4)

      String samplingMessage = """
					For a weather forecast (temperature is in Celsius): %s.
					At location with latitude: %s and longitude: %s.
					Please write an epic poem about this forecast using a Shakespearean style.
					""".formatted(weatherResponse.current().temperature_2m(), latitude, longitude);

      McpSchema.CreateMessageResult samplingResponse = exchange.createMessage(McpSchema.CreateMessageRequest.builder()
          .systemPrompt("You are a poet!")
          .messages(List.of(new McpSchema.SamplingMessage(McpSchema.Role.USER, new McpSchema.TextContent(samplingMessage))))
          .build()); // (5)

      epicPoem = ((McpSchema.TextContent) samplingResponse.content()).text();
    }

    // 100% progress
    exchange.progressNotification(new McpSchema.ProgressNotification(progressToken, 1.0, 1.0, "Task completed"));


		return epicPoem;
	}

	/**
	 * Get alerts for a specific area
	 * @param state Area code. Two-letter US state code (e.g. CA, NY)
	 * @return Human readable alert information
	 * @throws RestClientException if the request fails
	 */
	@Tool(description = "Get weather alerts for a US state. Input is Two-letter US state code (e.g. CA, NY)")
	public String getAlerts(@ToolParam( description =  "Two-letter US state code (e.g. CA, NY") String state) {
    WeatherClient.Alert alert = weatherClient.getAlert(state);

    return alert.features()
			.stream()
			.map(f -> String.format("""
					Event: %s
					Area: %s
					Severity: %s
					Description: %s
					Instructions: %s
					""", f.properties().event(), f.properties().areaDesc(), f.properties().severity(),
					f.properties().description(), f.properties().instruction()))
			.collect(Collectors.joining("\n"));
	}


  public static void main(String[] args) {
		WeatherMCP client = new WeatherMCP(new WeatherClient());
		System.out.println(client.getWeatherForecastByLocation(47.6062, -122.3321));
		System.out.println(client.getAlerts("NY"));
	}

}