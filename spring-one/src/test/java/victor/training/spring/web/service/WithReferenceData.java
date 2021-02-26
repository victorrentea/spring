package victor.training.spring.web.service;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Sql(value = "classpath:/reference-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithReferenceData {

}
