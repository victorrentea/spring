package victor.training.spring.gateway;

import org.springframework.util.MultiValueMap;

public class Utils {
  public static String extractUserFromUrl(MultiValueMap<String, String> queryParams) {

    if (queryParams.size()== 1 && queryParams.entrySet().iterator().next().getValue().get(0) == null)

      return queryParams.entrySet().iterator().next().getKey(); // ?admin

    if (queryParams.containsKey("user")) { // ?user=admin
      return queryParams.get("user").get(0);
    }
    if (queryParams.containsKey("u")) { // ?u=admin
      return queryParams.get("u").get(0);
    }
    return null;
  }
}
