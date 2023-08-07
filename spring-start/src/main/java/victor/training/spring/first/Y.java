package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.supb.X;

import javax.annotation.PostConstruct;
import java.util.List;

@Service // 1 single instance exists per app (.pod)
public class Y {
    private final X x;
//    private String currentUser; // WRONG> never store it in a field of a singleton
        // if you DO, you will get a RACE BUG: multiple threads (http req) will read/write the same GLOBAL value
    private final MailService mailService; // polymorphic injection
    private final String message;

    public Y(@Lazy X x,
//             @Qualifier("mailServiceImpl") MailService mailService,
//             MailServiceImpl mailService,
             MailService mailService,
             // i want when I start the app locally, the *DummyLocal version to be used, the Impl anywhere else
//             @Value("${message:defaultt}") String message) { // feels like a backdoor that no one should know about
             @Value("${message:defaultt}") String message) {
        this.x = x;
        this.mailService = mailService;
        this.message = message;
    }

    public int logic() {
        mailService.sendEmail("I like 4 topics : " + message);
        return 1;
    }
}
interface SpecialOffer {
    int getDiscount(String cart);
}

@Service
class SpecialOfferPerTotal implements SpecialOffer{
    @Override
    public int getDiscount(String cart) {
        return 1;
    }
}
@Service
class SpecialOfferPerCount implements SpecialOffer{
    @Override
    public int getDiscount(String cart) {
        return 2;
    }
}
@Service
@Order(1)
class SpecialOffer3For2 implements SpecialOffer{
    @Override
    public int getDiscount(String cart) {
        return 3;
    }
}

@Service
class PriceService {
    private final List<SpecialOffer> offers;
    PriceService(List<SpecialOffer> offers) {
        this.offers = offers;
    }

    public int computePrice(String cart, int basePrice) {
        System.out.println("Running offers : " + offers);
        int discount = offers.stream().mapToInt(offer -> offer.getDiscount(cart)).sum();
        return basePrice-discount;
    }

    @PostConstruct
    public void demo() {
        System.out.println("final price=" + computePrice("cart", 10));
    }
}