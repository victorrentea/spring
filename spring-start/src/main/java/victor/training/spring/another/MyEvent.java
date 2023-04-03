package victor.training.spring.another;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyEvent {
  private final String data;

  public String getData() {
    return data;
  }
}
