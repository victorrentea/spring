package victor.training.spring.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class CuValueuri {

//    @Value("${welcome.help.app-id}")
//    private final String p1;
//    @Value("${welcome.help.file}")
//    private final String p2;
    private final X x;

    @Autowired
    private String horror;

//    public CuValueuri(
//            @Value("${welcome.help.app-id}") String p1,
//            @Value("${welcome.help.file}")String p2,
//            X x) {
//        this.p1 = p1;
//        this.p2 = p2;
//        this.x = x;
//    }

    @PostConstruct
    public void method() {
//        System.out.println("p1:" + p1);
//        System.out.println("p2:" + p2);
        System.out.println("horror:" + horror);
    }

}

@Service
class X {

}
