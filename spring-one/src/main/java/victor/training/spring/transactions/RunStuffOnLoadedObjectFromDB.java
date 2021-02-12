package victor.training.spring.transactions;

import org.springframework.stereotype.Repository;

import java.util.List;

public class RunStuffOnLoadedObjectFromDB
{

}
@Repository
class SomeRepo {
   public List<MyEntity> method() {
   return null;
   }
}

class MyEntity {
   @RunAfterSELECT
   public void method() {

   }
}

@interface RunAfterSELECT{}
///
// you can write an @Aspect that intercepts all methods of every @Repository
// check if it returns a collection, and if so, calls the @runAfterSELECT methods
// in them, if any.