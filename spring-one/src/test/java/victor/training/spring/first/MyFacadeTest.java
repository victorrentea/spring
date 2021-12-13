package victor.training.spring.first;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MyFacadeTest {
   @Mock
   MyService myService;
   @Mock
   MyMapper mapper;
   @InjectMocks
   MyFacade facade;

   @Test
   void method() {

   }
}