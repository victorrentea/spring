package victor.training.spring.di;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public interface OInterfata {
     void method();
}
@Component
//@Profile("!ptUE")
class A implements  OInterfata {
    @Override
    public void method() {

    }
}
@Component
@Profile("ptUE")
@Primary
class B implements OInterfata {
    @Override
    public void method() {

    }
}
@Component
@Profile("ptSUA")
@Primary
class C implements OInterfata {
    @Override
    public void method() {

    }
}
