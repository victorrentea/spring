package victor.training.spring.transaction;


import org.slf4j.MDC;

public class TheMagicOfThreadLocals {

    public static void main(String[] args) {
        new TheMagicOfThreadLocals().high();
    }
    private static final ThreadLocal<String> OMG = new ThreadLocal<>();


    // where are thread locals used ?
    // @Transacational = JDBC Connecton
    // Session/EntityManager/PersistenceContext
    // SecurityContextHolder.getContext() ;; static method that gives you THE CURRENT USER!??
    // Apache Sleuth (traceid) = Locgback MDC


    public void high() {
        MDC.put("key","value");
        String conn = "here";
        OMG.set(conn);
        low();
    }

    private void low() {
        String conn = OMG.get();
        System.out.println("Conn: " + conn);
    }
}
