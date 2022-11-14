package victor.training.spring.di.different;

import org.springframework.stereotype.Service;
import victor.training.spring.di.ZInterface;

@Service
public class Y {
    private final ZInterface z;

    // #3 constructor injection (no @Autowired needed since Spring 4.3)
    public Y(ZInterface z) {
        this.z = z;
    }

    public int prod() {
        return 1 + z.prod();
    }
}
