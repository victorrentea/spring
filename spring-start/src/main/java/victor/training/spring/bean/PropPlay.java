package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class PropPlay {
//  @Value("${welcome.help.app-id2}") // crash the app as there is no prop anywhere with this name



  @Value("${welcome.help.app-id2:1204891}") // silently fallback to a default.
  // WHY THE HACK ISN'T THIS PART OF THE YAML ?? IS IT A BACKDOOR NO ONE SHOULD KNOW
  // instead => move the default in the yaml/properites to have all props listed there nicely./
  private int appId;
  @GetMapping("prop-failfast")
  public String yes() {
    return "Prop:" + appId;
  }

  @Autowired
  private Environment environment;
  @GetMapping("prop-bugtomorrow")
  public String dont() {
    return "Prop:" + environment.getProperty("welcome.help.app-id2");
  }

  @Autowired
  private ApplicationContext applicationContext;
  public void no() {
//    applicationContext.getBean(Person.class) // risky! fails at runtime
  }

  // yes:
  @Autowired
  private Person john;
}
