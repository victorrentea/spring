package victor.training.spring.transactions;

import org.springframework.stereotype.Service;

@Service
public class TranzactiiSiExceptii {
   private B b;

   public TranzactiiSiExceptii(B b) {
      this.b = b;
   }


//   public TranzactiiSiExceptii(B b) {
//      this.b = b;
//   }

   public String method() {
      return "A";
   }
}
class B {

}
//
//class ServDeBizTestul {
//   @Mock TranzactiiSiExceptii mock;
//   @InjectMocks ServDeBiz biz;
//
//   public void test() {
//       // test fericit
//   }
//}