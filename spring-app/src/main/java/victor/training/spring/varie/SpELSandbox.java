package victor.training.spring.varie;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
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


//@Configuration
class SpelConfiguration {
   @Value("#{T(java.lang.Math).random() lt 0.5f?'A Beautiful Day':null}")
   private String day;

   @Bean
   public UsingSpells usingSpells() {
      return new UsingSpells();
   }
   
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


class UsingSpells {
   @Value("#{sandbox.intProperty + 1}")
   private String s1;
   @Value("#{sandbox.stringProperty?.toUpperCase()}")
   private String s2;
   @Value("#{sandbox.stringProperty?:'Ploua'}")
   private String s3;
   @Value("#{sandbox.childList.?[intProperty gt 15]}")
   private List<SpELSandbox> children;
   @Value("#{sandbox.childList.?[stringProperty matches '.*e.*'].![intProperty]}")
   private List<Integer> childrenInts;


   @PostConstruct
   public void show() {
      System.out.println("-------------SPEL-----------");
      System.out.println("intProperty + 1 = " + s1);
      System.out.println("stringProperty.uppercase (null-safe) = " + s2);
      System.out.println("stringProperty or 'RAIN' if null = " + s3);
      System.out.println("children with intProperty > 15 = " + children);
      System.out.println("children with stringProperty containing 'e', intProperty of each = " + childrenInts);
      manualSpELPlay();
      System.out.println("-------------END SPEL-----------");
   }

   @Autowired
   private SpELSandbox sandbox;

   public void manualSpELPlay() {
      ExpressionParser parser = new SpelExpressionParser();
      Expression stringsOver20 = parser.parseExpression("stringProperty?.toUpperCase()?:'Ploua'");
      System.out.println(stringsOver20.getValue(new StandardEvaluationContext(sandbox)));
   }


}