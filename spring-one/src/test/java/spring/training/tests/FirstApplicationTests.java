package spring.training.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FirstApplicationTests {

	@Autowired
	private A a;

	@MockBean
	private SMSSender mockSmsSender;

	@Test
//	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void test1() {
		when(mockSmsSender.sendSMS(anyString())).thenReturn(true);
		boolean ok = a.m(1);
		System.out.println("testX");
		assertTrue(ok);
	}
	@Test(expected = IllegalStateException.class)
	public void test3() {
		 a.m(0);
	}

}
