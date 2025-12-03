package victor.training.spring.web.service;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AmOListaMareDarVreauSoParcurgInPagini {
  public static void main(String[] args) {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    var dtos = Lists.partition(numbers, 4)
        .stream() // 320 threaduri paralele
//        .parallelStream() // 320 threaduri paralele
        .map(page -> fetchPage(page))
        .flatMap(Collection::stream)
        .toList();
    // daca vrei musai poti sa rulezi parallelStreamu  de mai sus pe un FJP de-al tau cu cate threaduri vrei tu. -> chatGPT
    System.out.println(dtos);
  }

  private static List<String> fetchPage(List<Integer> pageIds) {
    return pageIds.stream().map(Objects::toString).toList();
  }
}
