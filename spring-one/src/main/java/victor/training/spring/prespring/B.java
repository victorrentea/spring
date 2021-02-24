package victor.training.spring.prespring;

import org.springframework.stereotype.Service;

@Service
public class B {

   private String currentUsername;

   public void setCurrentUsername(String currentUsername) {
      this.currentUsername = currentUsername;
   }

   // inturcat beanurile in spring sunt by default singletoane,
   // sa te fereasca Sf. Multithreading sa faci asta in app web.

   // pana si sonar stie ca o cauti cu lumanarea : https://rules.sonarsource.com/java/RSPEC-3749?search=injected


   public boolean isPrime(int n) {
      // ASDSAD
      System.out.println("Calculez pentru " + currentUsername);
      // ASDSAD
      return true;
   }
}
