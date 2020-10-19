package victor.training.spring.life;

public class Inainte {
   A a;

   public void setA(A a) {
      // IoC - Inversion of Control = tu nu-ti IEI singur ce ai nevoie, ci ti se da, in fct de ce declari ca vrei
      this.a = a;
   }

   // tu vrei sa testezi asta:
   public String method() {
//      A a = ServiceLocator.getObject(A.class);
      String s = a.met();
      return s.toUpperCase();
   }
}


class ServiceLocator {
   //aka ServiceRegistry
//   public static A getObject(Class<?> aClass) {
//      if din teste mi-a hackuit clasa A
//          return hackA;
//      else{
//         return new A();
//      }
//   }
   public void setFakeImplem(Class<?> c, Object o) {

   }
}

class A {

   public String met() { // vrei sa o mockuiesti
      return null;
   }
}