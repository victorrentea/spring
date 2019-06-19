package spring.training.resurse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SpELSandbox {

	private String stringProperty;
	private Integer intProperty;
	private Boolean booleanProperty;
	private List<String> stringList;
	private List<SpELSandbox> childList;

	public SpELSandbox() {
	}

	public SpELSandbox(Integer intProperty, String stringProperty) {
		this.stringProperty = stringProperty;
		this.intProperty = intProperty;
	}

	public String getStringProperty() {
		return stringProperty;
	}

	public void setStringProperty(String stringProperty) {
		this.stringProperty = stringProperty;
	}

	public Integer getIntProperty() {
		return intProperty;
	}

	public void setIntProperty(Integer intProperty) {
		this.intProperty = intProperty;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	public Boolean getBooleanProperty() {
		return booleanProperty;
	}

	public void setBooleanProperty(Boolean booleanProperty) {
		this.booleanProperty = booleanProperty;
	}

	public String randomToken() {
		return UUID.randomUUID().toString();
	}

	public List<SpELSandbox> getChildList() {
		return childList;
	}

	public void setChildList(List<SpELSandbox> childList) {
		this.childList = childList;
	}
}


@Configuration
class SpelConfiguration {
	@Bean
	public SpELSandbox sandbox(@Value("#{T(java.lang.Math).random() lt 0.5f?'A Beautiful Day':null}") String day) {
		SpELSandbox box = new SpELSandbox();
		box.setStringProperty(day);
		box.setBooleanProperty(true);
		box.setIntProperty(10);
		box.setChildList(Arrays.asList(
				new SpELSandbox(10, "One"),
				new SpELSandbox(20, "Two"),
				new SpELSandbox(30, "Three")
				));
		return box;
	}
}


@Component
class UsingSpells {
	@Autowired
}