package spring.training.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
class TranslatingExceptionsPlay implements CommandLineRunner {
    @Autowired
    private MessageSource messageSource;

    @Override
    public void run(String... args) {
        Locale locale = new Locale("RO", "RO");
        try {
            throwingBizMethod(-1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // TODO implement it in a @RestControllerAdvice
        }
    }

    private void throwingBizMethod(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Dear user, i must be negative, but given " + i);
        }
    }
}
