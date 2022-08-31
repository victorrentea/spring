package victor.training.spring.transaction.showcase;

import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest

public class Test1 {

    @Component
    static class One {}
}
