package victor.training.spring.strategy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public interface ConfigProvider {
	Properties getProperties();
}

@Component
@Profile("local")
class ConfigFileProvider implements ConfigProvider {
	public Properties getProperties() {
		try (InputStream is = ConfigFileProvider.class.getResourceAsStream("/application.properties")) {
			Properties properties = new Properties();
			properties.load(is);
			return properties; 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
@Profile("!local")
@Component
class ConfigDatabaseProvider implements ConfigProvider {

	public Properties getProperties() {
		// Real implem goes here
		Properties properties = new Properties();
		properties.setProperty("someProp", "from database");
		return properties; 
	}
	
}

