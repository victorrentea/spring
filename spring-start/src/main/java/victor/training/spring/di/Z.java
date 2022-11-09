package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class Z {

    private final Y y;
    @Lazy  // hack pentru vremuri de restriste cand te bati cu vreo circulara din frameowkr
    public Z(Y y) {
        this.y = y;
    }

    public int prod() {
        return 1;
    }
}
