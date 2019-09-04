package spring.training.first;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FirstApplicationTests {
	@Mock
	CA ca;
	@Mock
	CB cb;
	@InjectMocks
	C c;

	@Test
	public void contextLoads() {
//		C c = new C(ca, cb);
		Mockito.when(ca.m()).thenReturn(5);
		Mockito.when(cb.m()).thenReturn(5);
		assertEquals(10, c.m());
	}

}
