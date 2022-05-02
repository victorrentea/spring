package victor.training.spring.di;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class XTest {
   @Mock
   Y y;
   @InjectMocks
   X x;

   @Test
   void prod() {
      Mockito.when(y.prod()).thenReturn(1);

      int actual = x.prod();


//      new Y(new Z())

      Assertions.assertThat(actual).isEqualTo(2);
   }
}