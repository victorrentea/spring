package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private String welcomeMessage;
    private List<String> supportUrls;
    private Map<String,String> localContactPhone; // per country
    private HelpInfo help;

    @PostConstruct
    public void printMyself() {
        log.debug("My props: " + this);
    }
}

@Data
class HelpInfo {
    private URI helpUrl;
    private String iconUri;
}

//@Value
//class Pair {
//    int x,y;
//}

// ~~~~~~~~

//@Data
//class Pair {
//    private final int x;
//    private final int y;
//}

// ~~~~~~~~

class Pair {
    private final int x;
    private final int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return x == pair.x &&
                y == pair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}