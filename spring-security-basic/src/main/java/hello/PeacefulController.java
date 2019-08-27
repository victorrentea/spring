package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class PeacefulController {

    public String rest() {
        return "Peace on you";
    }
}
