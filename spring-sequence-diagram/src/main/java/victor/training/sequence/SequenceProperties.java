package victor.training.sequence;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * Everything under {@code sequence.*}. Bound by hand rather than as a bean: the advisor and the
 * post-processors that need it are created before ordinary beans exist.
 *
 * @param enabled         false turns the whole thing off
 * @param basePackages    whose beans become participants; empty = the package of your @SpringBootApplication
 * @param outputDir       where the .svg/.puml files and index.html go
 * @param showConnections draw taking a JDBC connection from the pool and giving it back
 * @param keepInMemory    how many diagrams the live page remembers
 * @param maxLabelLength  longer SQL is cut with …
 * @param http            a diagram per HTTP request
 */
public record SequenceProperties(
    @DefaultValue("true") boolean enabled,
    @DefaultValue List<String> basePackages,
    @DefaultValue("target/sequence") String outputDir,
    @DefaultValue("true") boolean showConnections,
    @DefaultValue("30") int keepInMemory,
    @DefaultValue("110") int maxLabelLength,
    @DefaultValue Http http) {

  /**
   * @param enabled       false = no diagram per request, no /sequence page
   * @param excludedPaths request paths starting with any of these are not drawn
   */
  public record Http(
      @DefaultValue("true") boolean enabled,
      @DefaultValue({"/sequence", "/h2-console", "/actuator", "/favicon.ico", "/error"}) List<String> excludedPaths) {
  }

  public static SequenceProperties from(Environment env) {
    return Binder.get(env).bindOrCreate("sequence", SequenceProperties.class);
  }
}
