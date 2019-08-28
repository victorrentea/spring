package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnController {
    @GetMapping("redirect")
    public String redirect() {
        return "redirect:https://myhost.com/some/arbitrary/path";
    }
}
