package victor.training.spring.di.different;

import org.springframework.stereotype.Service;
import victor.training.spring.di.Z;

@Service
public class Y {
    private final Z z;

    // #3 constructor injection (no @Autowired needed since Spring 4.3)
    public Y(Z z) {
        this.z = z;
    }

    public int prod() {
        return 1 + z.prod();
    }
}
