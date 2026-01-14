package victor.training.spring.ai;

import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiMcpServerApp {

	public static void main(String[] args) {
		SpringApplication.run(AiMcpServerApp.class, args);
	}

	@Bean
	MethodToolCallbackProvider methodToolCallbackProvider(SmsSenderTool smsSenderTool) {
    return MethodToolCallbackProvider.builder().toolObjects(smsSenderTool).build();
	}

}