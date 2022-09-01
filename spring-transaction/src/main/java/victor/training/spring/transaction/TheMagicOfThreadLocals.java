package victor.training.spring.transaction;


public class TheMagicOfThreadLocals {

    public static void main(String[] args) {
        new TheMagicOfThreadLocals().high();
    }
    private static final ThreadLocal<String> OMG = new ThreadLocal<>();

    public void high() {
        String conn = "here";
        OMG.set(conn);
        low();
    }

    private void low() {
        String conn = OMG.get();
        System.out.println("Conn: " + conn);
    }
}
