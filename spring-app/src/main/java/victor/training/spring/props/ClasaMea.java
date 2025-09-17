package victor.training.spring.props;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClasaMea {
  @Autowired
//  @Qualifier("api") // numele beanului mentionat ca string
//  OpenAPI x;
  OpenAPI api;
}
