package victor.training.spring.bean;

public class CumMergeBeanOverride {
    public static void main(String[] args) {
        new SubClasa().conversation();
    }
}
// pe asta Springu o genereaza la runtime automat invizibil, doar pentru clase @Configuration
class SubClasa extends OClasaConfiguration {
    @Override
    public Person john() {
        System.out.println("Ti-am furat apelul");
        return super.john();
    }
}
