package victor.training.spring.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

public interface FetchExtraDataService {
    String fetch(int id);
}
@Component
class PeBune implements  FetchExtraDataService {
    @Override
    public String fetch(int id) {
        // REST TEMPLATE
        return null;
    }
}
@Component
@Profile("local")
@Primary
class PtLocaluMeu implements  FetchExtraDataService {
    @Override
    public String fetch(int id) {
        return "dummy";
    }
}
@Service
@RequiredArgsConstructor
class LogicaTa {
    private final FetchExtraDataService interfata;

//
//    @Transactional
//    public void a() {
//        try {
//            b();
//        } catch (Exception e ){
//            // las ca merge ci cu exc
//        }
//    }
//
//    public void b() {
//
//    }
//
//    @Tras
//    public void method() {
//        extracted();
//    }

    private void extracted() {
        System.out.println("LUCREZ CU " + interfata.getClass());
    }
}