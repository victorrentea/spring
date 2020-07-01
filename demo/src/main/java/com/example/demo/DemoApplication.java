package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class DemoApplication {

   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }
}


class DBManager {
   static DataSource getDataSource() {
      return null;
   }
}

class A {
   @Autowired
   private RepoImpl repo;

   public void m() {
      repo.getUserById(1L);
   }
}


class B {
   @Autowired
   private RepoImpl repo;
   public void m() {
      repo.getUserById(1L);
   }
}

@Service
class B2 {
   @Autowired
//   @Qualifier("dr")
//   private IRepo repo;

   // daca tot stii precis pe care-l vrei, prefera:
   private DummyRepo repo;

   @Autowired
   private Map<String, IRepo> beanNameToImpl;

   @Autowired
   private List<IRepo> allImplems;

   @PostConstruct
   public void printAll() {
      System.out.println("repo: " +repo);
      System.out.println("map: " +beanNameToImpl);
      System.out.println("list: " +allImplems);
   }
   public void m2() {
      repo.getUserById(1L);
   }
}

interface IRepo {
   void getUserById(long l);
}

// pentru teste, decorator, proxy te imping spre OOP -> met nestatice
// pus pentru teste in src/test/java
@Repository("dr")
class DummyRepo implements IRepo {
   //	Map<>
   public void getUserById(long l) {
   }
}

@Primary
@Repository
class RepoImpl implements IRepo {
   //   @Autowired
   private X x;
   private Y y;

   @Autowired // method-injection
   public void init3(X x ,Y y) {
      System.out.println("1");
      this.x = x;
      this.y = y;
   }

   // chemat de Spring
   @Autowired // method-injection
   public void init2(X x ,Y y) {
      System.out.println("2");
      this.x = x;
      this.y = y;
   }

   // chemat de Spring
   @PostConstruct
   public void afterinjection() {
      System.out.println(" X= " + x);
      System.out.println("Y= " + y);
   }

   public void getUserById(long l) {
      System.out.println("N-o chem");
//		dataSource.getConnection();
   }
}

@Value
class Rec {
   int x;
   String s;
}
// ~= record Rec(int x, String s) {  Java 17

@Slf4j
@Service
@RequiredArgsConstructor
class X {
//   private static final Logger log = LoggerFactory.getLogger(X.class);
   // <property name="xa" .../>
   private final XA xa;
   private final Y y;

}

@Service
    // <bean>
class XA {

}

@Component
class Y {
}
