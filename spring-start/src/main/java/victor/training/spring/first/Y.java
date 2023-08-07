package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.supb.X;

@Service
public class Y {
    private final X x;
    private final MailService mailService; // polymorphic injection
    private final String message = "HALO";

    public Y(@Lazy X x,
//             @Qualifier("mailServiceImpl") MailService mailService,
//             MailServiceImpl mailService,
             MailService mailService
             // i want when I start the app locally, the *DummyLocal version to be used, the Impl anywhere else
    ) {
        this.x = x;
        this.mailService = mailService;
    }

    public int logic() {
        mailService.sendEmail("I like 4 topics : " + message);
        return 1;
    }
}
