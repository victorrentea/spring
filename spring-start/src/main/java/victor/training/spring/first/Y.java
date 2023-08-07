package victor.training.spring.first;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import victor.training.spring.supb.X;

import javax.annotation.PostConstruct;
import java.util.List;

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