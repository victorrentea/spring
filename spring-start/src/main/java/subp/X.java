package subp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.spring.di.Y;

@Component // = o porcarie. ceva f tehnic. un fel de util.

//@Controller // se folosea in MVC, .jsp, jsf, vaadin, velocity, freemarker => genereaza HTML pe server
//@RestController // intoarce JSON -> SinglePageApp (ng, react, vue)
public class X {
    // #1 field injection
    @Autowired
    private Y y;

    // #2 method (setter) injection (rarely used)
    //	private Z z;
    //	@Autowired
    //	public void setZ(Z z) {
    //		this.z = z;
    //	}

    public int prod() {
        return 1 + y.prod();
    }
}
