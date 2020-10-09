package victor.training.spring.web.spring.catalin;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BizCodeTest {

   @InjectMocks
   private BizCode bizCode;

   @Mock
   private DataRepo1 dataRepo1;
   @Mock
   private DataRepo2 dataRepo2;


   @Test
   public void test() {
      Mockito.when(dataRepo1.method()).thenReturn(1);
      Mockito.when(dataRepo2.method()).thenReturn(2);
      Assertions.assertThat(bizCode.method()).isEqualTo(3);
   }
}
