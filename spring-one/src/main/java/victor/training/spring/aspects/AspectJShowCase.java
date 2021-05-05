package victor.training.spring.aspects;

public class AspectJShowCase {

   static {
      System.out.println("AGENT LOADED: " + isAspectJAgentLoaded());
   }
   public static boolean isAspectJAgentLoaded() {
      try {
         org.aspectj.weaver.loadtime.Agent.getInstrumentation();
      } catch (NoClassDefFoundError | UnsupportedOperationException e) {
         return false;
      }
      return true;
   }

   public static void staticMethod() {
      new AspectJShowCase().publicMethodOnNewInstance();
   }

   public void publicMethodOnNewInstance() {
      privateMethod();
   }

   private void privateMethod() {

   }
}
