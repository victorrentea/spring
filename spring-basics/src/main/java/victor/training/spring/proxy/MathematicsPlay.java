package victor.training.spring.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class MathematicsPlay implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MathematicsPlay.class, args);
    }
    @Autowired
    private IMathematics m;

    @Override
    public void run(String... args) throws Exception {
//        bizLogic();
    }

    public Integer bizLogic() {
        return m.sum(1, 1) + m.product(2,2);
    }
}
interface IMathematics {
    Integer sum(int a, int b);
    Integer product(int a, int b);
}
@Slf4j
@Service
@Primary
class MathematicsWithLogging implements IMathematics {
    private final Mathematics realStuff;
    public MathematicsWithLogging(Mathematics realStuff) {
        this.realStuff = realStuff;
    }
    public Integer sum(int a, int b) {
        log.debug("sum({},{})",a,b);
        return realStuff.sum(a, b);
    }

    @Override
    public Integer product(int a, int b) {
        log.debug("product({},{})",a,b);
        return realStuff.product(a, b);
    }
}

@Service
class Mathematics implements IMathematics {
    public Integer sum(int a, int b) {
        return a + b;
    }
    @Override
    public Integer product(int a, int b) {
        return a * b;
    }
}
