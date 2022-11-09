package victor.training.spring.life;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnController {

    private String camp; // <== NU CUMVA sa tii date specifice unui request in campul unui singleton

    @GetMapping
    public void method(@RequestParam String date) {
        camp = date;
        // chestii
        System.out.println(camp);
    }
}
