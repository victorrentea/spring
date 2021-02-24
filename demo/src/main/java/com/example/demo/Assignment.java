package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
class A {
   private E e;
   private S s;
   @Autowired
   private B b;

   @Autowired
   public void setE(E e, S s) {
      this.e = e;
      this.s = s;
   }

   @PostConstruct
   public void salut() {
      System.out.println("Salut!");
   }
}
@Service
class E {
}
@Service
class B {
   private C c;

   public B(C c) {
      this.c = c;
   }
}
@Service
class C {
   private final S s;
   private final D d;

   C(S s, ApplicationContext applicationContext) {
      this.s = s;
      this.d = applicationContext.getBean(D.class);
   }
}

interface D {
}

@Component
@Primary
@Profile("d1")
class D1 implements D {

}

@Component
class D2 implements D {

}
