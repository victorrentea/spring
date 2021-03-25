package victor.training.spring.history;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ArcheologyTest {

   @Mock
   private Other otherMock;

   @InjectMocks
   Test1 a;

   @Test
   void methodToTest() {

      Mockito.when(otherMock.met()).thenReturn(9);
      assertThat(a.methodToTest()).isEqualTo(10);

   }
}