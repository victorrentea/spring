package subp;

import lombok.RequiredArgsConstructor;
import victor.training.spring.di.Y;

//@Configuration// nu pentru clase ce contin logica de-a mea, ci doar pentru a defini @Bean sau ajusta defaulturi de prin spring

// semantica = layere
//@Service // defineste un bean in context de tip "X"
//@Repository // nu mai e necesar daca folosesti spring  Data (extinzi din JpaRepository/ Mongo.. / CrudRepository)
@RequiredArgsConstructor
@Mapper
//@Component // = o porcarie. ceva f tehnic. un fel de util.

//@Controller // se folosea in MVC, .jsp, jsf, vaadin, velocity, freemarker => genereaza HTML pe server
//@RestController // intoarce JSON -> SinglePageApp (ng, react, vue)
public class X {
    // #1 field injection = cel mai frecvent in codebaseul azi
    private final Y y;
    private final Y y2;
    private final Y y3;
    private final Y y4;
    private final Y y5;
    private final Y y6;
    private final Y y7;
    private final Y y8;

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
