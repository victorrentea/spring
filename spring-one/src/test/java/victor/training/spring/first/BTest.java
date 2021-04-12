package victor.training.spring.first;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BTest {
   @Mock
   A aMock;
   @InjectMocks
   B b;// = new B(aMock);

   @Test
   public void test() {

      Mockito.when(aMock.gimme()).thenReturn("a");

      assertEquals("A", b.method());
   }
}