package victor.training.spring.first;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class XTest {
   @InjectMocks
//       @Autowired
   X x;

   @Mock
   Y y;

   @Test
   void test() {
//      new X(y)
//      X x = new X();
//      x.y = ????

      when(y.something()).thenReturn(3);
//      X x = new X();
      // reflection magic to inject the private dep
      int actual = this.x.toTest();

      assertThat(actual).isEqualTo(4);
   }

}