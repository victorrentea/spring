package victor.training.spring;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Play {
  private final String a;
  private final String b;
}

class Jr {
  public static void main(String[] args) {
    Play play = new Play("a", "b");
  }
}
//
//record java17(
//        String a,
//        String b,
//
//) {}