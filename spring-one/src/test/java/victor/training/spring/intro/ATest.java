package victor.training.spring.intro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ATest {
   @InjectMocks
   private A a;
   @Mock
   private B b;

   @Test
   public void test() {
      when(b.method()).thenReturn(1);

      assertEquals(2, a.method());
   }
}