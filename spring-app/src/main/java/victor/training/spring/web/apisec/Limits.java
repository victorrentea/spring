package victor.training.spring.web.apisec;

import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RestController
public class Limits {

  @GetMapping("api/leaderboard")
  public Page<Leader> leadearboardPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
    return queryTopPlayers(page, size);
  }

  private Page<Leader> queryTopPlayers(int page, int size) {
    List<Leader> data = IntStream.range(0, size)
            .limit(20_000_000)
            .map(i -> i + (page - 1) * size)
            .peek(i -> {
              if (i % 1000 == 0) ThreadUtils.sleepMillis(1);
            }) // emulate DB
            .mapToObj(i -> new Leader("Leader" + i, Integer.MAX_VALUE - i*17))
            .collect(toList());
    return new PageImpl<>(data, Pageable.ofSize(size), 20_000_000);
  }

  @Value
  public static class Leader {
    String name;
    int points;
  }
}
