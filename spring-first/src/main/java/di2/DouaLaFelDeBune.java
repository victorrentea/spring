package di2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DouaLaFelDeBune {
    @Autowired
    @Qualifier("vesela")
    private Fata fata;
    @Autowired
    private List<Fata> fetze; // mecanisme de filtre sau pluginuri
    @PostConstruct
    public void post() {
        System.out.println(fata.getClass());
        System.out.println(fetze);
    }
}

interface Fata {

}

@Service
class Trista implements Fata {
}

@Service
class Vesela implements Fata {
}