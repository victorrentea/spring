package di2;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class Test {
    @Autowired
    private IConfig config;
    @Autowired
    private ISmsSender smsSender;

    @org.junit.Test
    public void t1() {
        System.out.println(config.load());
        smsSender.sendSms();
    }
}
