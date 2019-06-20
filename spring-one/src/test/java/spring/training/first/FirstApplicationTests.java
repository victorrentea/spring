package spring.training.first;

import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({
		"a=b",
		"c=d"
})
//	@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // arunca la gunoi contextul curent DUPA testul asta.
public class FirstApplicationTests {

	@MockBean
	private SmsSender mockSmsSender;

	@Autowired
	MaiESiAsta grea;
	@Test
	public void contextLoads() {
//		ConfigurableWebServerApplicationContext c;
//		c.bean

		Mockito.when(mockSmsSender.send(ArgumentMatchers.anyString())).thenReturn(true);
		grea.greuTata(11);
		Mockito.verify(mockSmsSender).send("Ti-am dat un bip. Si sunt voinic!");
	}
	@DirtiesContext // arunca la gunoi contextul curent DUPA testul asta.
	@Test
	public void test2() {
		Mockito.when(mockSmsSender.send(ArgumentMatchers.anyString())).thenReturn(true);
		grea.greuTata(11);
		Mockito.verify(mockSmsSender).send("Ti-am dat un bip. Si sunt voinic!");
	}

}




@Data
@Service
class MaiESiAsta {
	private final LogicaGrea logicaGrea;
	public void greuTata(int i) {
		logicaGrea.greuTata(i);
	}
}

@Data
@Service
class LogicaGrea {
	private final SmsSender smsSender;

	public void greuTata(int i) {
		//mai mult rahat turcesc aici
		if (i > 10) {
			smsSender.send("Ti-am dat un bip. Si sunt voinic!");
		}
	}

}

@Component
class SmsSender {
	public boolean send(String sms) {
		System.out.println("cost 0.7 centi");
		return true;
	}
}