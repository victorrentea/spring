package victor.training.spring.life;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {

}

//@Service
//@Repository
//@Controller
//@RestController
//@Configuration //@ServiceActivator
@Facade
class B {
   private final A a;

   public B(A a) {
      this.a = a;
   }

   // singurul motiv real pt method injection 0.1%

   private TransactionTemplate txTemplate;

   @Autowired
   public void setTransactionManager(PlatformTransactionManager transactionManager) {
      // IoC - Inversion of Control = tu nu-ti IEI singur ce ai nevoie, ci ti se da, in fct de ce declari ca vrei
      this.txTemplate = new TransactionTemplate(transactionManager);
      txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
   }

   @PostConstruct
   public void initialize() { // ruleaza DUPA constructor si DUPA ce au fost injectate toate dependintele
      System.out.println(method());
   }


   // tu vrei sa testezi asta:
   public String method() {
//      A a = ServiceLocator.getObject(A.class);
      String s = a.met();
      return s.toUpperCase();
   }
}


@Component
class C {
}
@Component
class A {
   public String met() { // vrei sa o mockuiesti
      return "string";
   }
}