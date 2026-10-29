package victor.training.sequence;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.transaction.TransactionExecutionListener;
import org.springframework.web.servlet.function.RouterFunction;

import static org.assertj.core.api.Assertions.assertThat;

/** Each part switches on only with what it observes, and the whole thing switches off with one property. */
class SequenceAutoConfigurationTest {
  private final ApplicationContextRunner runner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(SequenceAutoConfiguration.class))
      .withPropertyValues("sequence.base-packages=sample");

  @Test
  void onByDefault() {
    runner.run(context -> assertThat(context)
        .hasSingleBean(SequenceRecorder.class)
        .hasBean("sequenceCallTracingAdvisor")
        .hasBean("sequenceDataSourceProxy")
        .hasBean("sequenceTransactionListener")
        .doesNotHaveBean(FilterRegistrationBean.class));
  }

  @Test
  void offWithOneProperty() {
    runner.withPropertyValues("sequence.enabled=false")
        .run(context -> assertThat(context).doesNotHaveBean(SequenceRecorder.class));
  }

  @Test
  void noTransactionFramesWithoutSpringTx() {
    runner.withClassLoader(new FilteredClassLoader(TransactionExecutionListener.class))
        .run(context -> assertThat(context).hasSingleBean(SequenceRecorder.class).doesNotHaveBean("sequenceTransactionListener"));
  }

  @Test
  void aServletAppGetsADiagramPerRequestAndTheLivePage() {
    new WebApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(SequenceAutoConfiguration.class))
        .withPropertyValues("sequence.base-packages=sample")
        .run(context -> assertThat(context).hasSingleBean(FilterRegistrationBean.class).hasSingleBean(RouterFunction.class));
  }

  @Test
  void withoutSpringBootApplicationTheBasePackageMustBeSet() {
    new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(SequenceAutoConfiguration.class))
        .run(context -> assertThat(context).hasFailed()
            .getFailure().rootCause().hasMessageContaining("sequence.base-packages"));
  }
}
