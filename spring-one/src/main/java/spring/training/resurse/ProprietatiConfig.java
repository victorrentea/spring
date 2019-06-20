package spring.training.resurse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Locale;

@SpringBootApplication
public class ProprietatiConfig implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ProprietatiConfig.class);
    }

    @Value("${parola.critica:12345678}")
    String parola;


    @Bean
    public ClasaVeche clasaVeche() {
        return ClasaVeche.getInstance();
    }




    @Bean
    public AltaClasa altaClasa(
            @Value("${parola.critica:12345678}") String parola,
            ClasaVeche clasaVeche) {
        System.out.println("In altaClasa()");
        return new AltaClasa(clasaVeche, parola, clasaVeche.getCurrentIp().split("\\."));
    }
    @Bean
    public FinalaMica mica(AltaClasa altaClasa) {
        System.out.println("Instantiez finala mic");
        return new FinalaMica(altaClasa);
    }
    @Bean
    public FinalaMica finalaMica2(AltaClasa altaClasa) {
        System.out.println("Instantiez finala mic");
        return new FinalaMica(altaClasa);
    }

    @Autowired
    private MessageSource messageSource;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Parola este: " +parola);
        Locale locale = new Locale("RO", "RO");
        try {
            altaMetoda(-1);
        } catch (MyException e) {
            String key = e.getCode().code;
            String message = messageSource.getMessage(key, null, locale);
            System.out.println("Fraere: eroare: " + message);
        }

    }

    private void altaMetoda(int i) {
        if (i < 0) {
            throw new MyException(MyException.ErrorCode.I_NEGATIV);
        }
    }
}
class MyException extends RuntimeException {

    enum ErrorCode {
        I_NEGATIV("error.i.negativ");

        public final String code;

        ErrorCode(String code) {

            this.code = code;
        }
    }
    private ErrorCode code;

    public MyException(ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }

    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

//class CeFaceSpring extends ProprietatiConfig {
//    @Override
//    public AltaClasa altaClasa() {
//        if (amdeja creeata AltaClasa) {
//            return din galeata;
//        } else {
//        AltaClasa instanta = super.altaClasa();
//        instanta.hello();
//        pune in galeata
//        return instanta;
//    }
//}

//@Component
@RequiredArgsConstructor
class AltaClasa {
    private final ClasaVeche veche;
    private final String parola;
    private final String[] ipParts;
    @PostConstruct
    public void hello() {
        System.out.println("Post Construct in AltaClasa");
        veche.metoda();
    }
}

//@Component
@RequiredArgsConstructor
class FinalaMica {
    private final AltaClasa altaClasa;
    @PostConstruct
    public void hellomica() {
        System.out.println("Finala mica");
    }
}


// n-ai access s-o schimbi
class ClasaVeche {
    private static ClasaVeche INSTANCE;
    private ClasaVeche() {
        currentIp = resolveIp();
    }

    private String resolveIp() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    public static ClasaVeche getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClasaVeche();
        }
        return INSTANCE;
    }

    private String currentIp;

    public String getCurrentIp() {
        return currentIp;
    }

    public void metoda() {
        System.out.println("Hello worlds");
    }
}
