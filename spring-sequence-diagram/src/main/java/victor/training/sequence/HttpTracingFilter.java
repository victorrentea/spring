package victor.training.sequence;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/** Every HTTP request is a diagram, drawn as soon as the response is written. */
class HttpTracingFilter extends OncePerRequestFilter {
  private final SequenceRecorder recorder;
  private final List<String> excludedPaths;

  HttpTracingFilter(SequenceRecorder recorder, List<String> excludedPaths) {
    this.recorder = recorder;
    this.excludedPaths = excludedPaths;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI().substring(request.getContextPath().length());
    return excludedPaths.stream().anyMatch(path::startsWith);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String label = request.getMethod() + " " + request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    Span span = recorder.startRoot(SequenceRecorder.KIND_HTTP, "Client", label);
    try (Scope ignored = span.makeCurrent()) {
      chain.doFilter(request, response);
      span.setAttribute(SequenceRecorder.HTTP_STATUS, (long) response.getStatus());
    } catch (IOException | ServletException | RuntimeException e) {
      span.setAttribute(SequenceRecorder.HTTP_STATUS, 500L);
      throw e;
    } finally {
      recorder.end(span);
    }
  }
}
