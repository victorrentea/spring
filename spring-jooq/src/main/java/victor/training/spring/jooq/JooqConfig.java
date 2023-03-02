package victor.training.spring.jooq;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

  //  @Autowired
  //  private DataSource dataSource;
  //  @Bean
  //  public DataSourceConnectionProvider connectionProvider() {
  //    return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
  //  }
  //  @Bean
  //  public DefaultDSLContext dsl() {
  //    return new DefaultDSLContext(configuration());
  //  }
  //
  //  public DefaultConfiguration configuration() {
  //    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
  //    jooqConfiguration.set(connectionProvider());
  //    jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTranslator()));
  //    return jooqConfiguration;
  //  }
  //
  //  @Bean
  //  public ExceptionTranslator exceptionTranslator() {
  //    return new ExceptionTranslator();
  //  }
}
