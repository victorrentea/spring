package spring.training.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
class Inocenta {
    public void m() {
        System.out.println("ceva in felul ei");
    }
    public void m2() {
        System.out.println("ceva in felul ei");
    }
    public void m3() {
        System.out.println("ceva in felul ei");
    }
    public void m4() {
        System.out.println("ceva in felul ei");
    }
    public void m5() {
        System.out.println("ceva in felul ei");
    }
    public void m6() {
        System.out.println("ceva in felul ei");
    }
}
///  ulterior, un client plate$te bani grei pentru ca m() sa faca "alt lucru"

@Service
@Primary
class Hackareala extends Inocenta {
    @Override
    public void m() {
        System.out.println("alt lucru");
    }
}