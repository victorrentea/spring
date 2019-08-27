package spring.training.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
public class SpELSandbox {
	private String stringProperty;
	private Integer intProperty;
	private Boolean booleanProperty;
	private List<SpELSandbox> childList;

	public SpELSandbox() {
	}

	public SpELSandbox(Integer intProperty, String stringProperty) {
		this.stringProperty = stringProperty;
		this.intProperty = intProperty;
	}

	public String randomToken() {
		return UUID.randomUUID().toString();
	}
}


@Configuration
class SpelConfiguration {
	@Value("#{T(java.lang.Math).random() lt 0.5f?'A Beautiful Day':null}")
	private String day;

	@Bean
	public SpELSandbox sandbox() {
		SpELSandbox box = new SpELSandbox();
		box.setIntProperty(10);
		box.setStringProperty(day);
		box.setBooleanProperty(true);
		box.setChildList(Arrays.asList(
				new SpELSandbox(10, "One"),
				new SpELSandbox(20, "Two"),
				new SpELSandbox(30, "Three")
				));
		System.out.println("Sandbox: " + box);
		return box;
	}
}


@Component
class UsingSpells {
	@Value("#{sandbox.intProperty + 1}")
	private String s1;
	@Value("#{sandbox.stringProperty}")
	private String s2;
	@Value("#{sandbox.stringProperty?.toUpperCase()?:'Ploua'}")
	private String s3;
	@Value("#{sandbox.childList.?[intProperty gt 15]}")
	private List<SpELSandbox> children;
	@Value("#{sandbox.childList.?[intProperty gt 15].![intProperty]}")
	private List<Integer> childrenInts;
	@Value("#{sandbox.randomToken()}")
	private String randomToken;
	@Autowired
	private SpELSandbox sandbox;

	@PostConstruct
	public void show() {
		System.out.println("-------------SPEL-----------");
		System.out.println(s1);
//		System.out.println(s2);
//		System.out.println(s3);
//		System.out.println(children);
//		System.out.println(childrenInts);
//		System.out.println(randomToken);
//		manualSpELPlay(sandbox);
		System.out.println("-------------END SPEL-----------");
	}

	public static void manualSpELPlay(Object root) {
		ExpressionParser parser = new SpelExpressionParser();
		Expression stringsOver20 = parser.parseExpression("stringProperty?.toUpperCase()?:'Ploua'");
		System.out.println(stringsOver20.getValue(new StandardEvaluationContext(root)));
	}


}