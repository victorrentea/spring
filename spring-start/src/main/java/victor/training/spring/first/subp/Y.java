package victor.training.spring.first.subp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("someName")
public class Y {
}


@Component
@RequiredArgsConstructor
class SomeDependent {

  private final Y y;
}