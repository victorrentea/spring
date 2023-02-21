package victor.training.spring.security.apisec;

import lombok.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RestController
public class ObjectLevelAuthorization {
//  @PermitAll
  @GetMapping("api/shops/{shopId}/revenue-data.json")
  public Map<String, Double> getRevenueData(@PathVariable int shopId) {
    Random r = new Random(shopId);
    Map<String, Double> results = new LinkedHashMap<>();
    for (int i = 1; i <= 12; i++) {
      double sales = (r.nextInt(100000_00) + 1000_00) / 100.0;
      String month = LocalDate.now().minusMonths(i).format(DateTimeFormatter.ofPattern("MMM yyyy"));
      results.put(month, sales);
    }
    return results;
  }

  @GetMapping("api/shops/my")
  public ShopDto getMyShop() {
    return new ShopDto(getMyShopId());
  }
  private int getMyShopId() {
    String user = SecurityContextHolder.getContext().getAuthentication().getName();
    return user.equals("admin") ? 12 : 2;
  }

  @GetMapping("api/shops")
  public List<ShopDto> getAllShops() {
    List<ShopDto> result = IntStream.range(1, 1000).mapToObj(ShopDto::new).collect(toList());
    Collections.shuffle(result);
    return result;
  }
  @Value
  static class ShopDto {
    int id;
    String name;
    public ShopDto(int i) {
      id = i;
      name = "Shop #" + i;
    }
  }
}