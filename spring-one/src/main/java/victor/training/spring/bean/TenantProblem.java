package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Configuration
public class TenantProblem  implements CommandLineRunner {
    @Autowired
    private ServiceResolver resolver;
    @Override
    public void run(String... args) throws Exception {
        resolver.resolve("RO").m();
        resolver.resolve("HU").m();
    }

//    @Bean("CORE-accountCoreLevel3Service")
//    public IService n() {
//        return new Level3Service();
//    }
}
@Component
class ServiceResolver {
    private final List<IService> allImplems;

    public ServiceResolver(List<IService> allImplems) {
        this.allImplems = allImplems;
    }

    public IService resolve(String countryIso) {
        return allImplems.stream().filter(i -> i.accepts(countryIso, DB.USER)).findFirst().get();
    }
}
enum DB {
    USER, PROFILE
}

interface IService {
    boolean accepts(String countryIso, DB db);
    void m();
}
@Service
class ROServiceDebit implements IService {
    @Override
    public boolean accepts(String countryIso, DB db) {
        return "RO".equals(countryIso);// && db...;
    }

    @Override
    public void m() {
        System.out.println("RO implem");
    }
}
@Service
class ROServiceCurent implements IService {
    @Override
    public boolean accepts(String countryIso, DB db) {
        return "RO".equals(countryIso);// && db...;
    }

    @Override
    public void m() {
        System.out.println("RO implem");
    }
}
//@Debit
@Service
class HUServiceDebit implements IService {
    @Override
    public boolean accepts(String countryIso, DB db) {
        return "RO".equals(countryIso);// && db...;
    }

    @Override
    public void m() {
        System.out.println("RO implem");
    }
}
//@Curent
@Service
class HUServiceCurent implements IService {
    @Override
    public boolean accepts(String countryIso, DB db) {
        return "RO".equals(countryIso);// && db...;
    }

    @Override
    public void m() {
        System.out.println("RO implem");
    }
}


@Service
class DefaultService implements IService {
    @Override
    public boolean accepts(String countryIso, DB db) {
        return true;
    }

    @Override
    public void m() {
        System.out.println("Default implem");
    }
}
