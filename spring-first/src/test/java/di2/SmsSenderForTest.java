package di2;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class SmsSenderForTest implements ISmsSender {
    @Override
    public void sendSms() {
        System.out.println("NIMIC!");
    }
}
