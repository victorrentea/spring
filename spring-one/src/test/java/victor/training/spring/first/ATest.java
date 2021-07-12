package victor.training.spring.first;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

//@RegisterExtension(MockitoExtension.class)
class ATest {

   @Mock
   private B b;

   @InjectMocks
   private A a;


   @Test
   void method() {
   }
}