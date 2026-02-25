package victor.training.spring.web;

import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

// make this extends a Spring specific exception, so that it is handled by the Spring exception handling mechanism and converted to a proper HTTP response
public class DuplicateTrainingNameException extends HttpStatusCodeException {
  public DuplicateTrainingNameException(String name) {
    super(BAD_REQUEST, "Another training with that name already exists: " + name);
  }
}
