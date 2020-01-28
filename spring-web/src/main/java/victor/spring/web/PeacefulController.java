package victor.spring.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeacefulController {



    @GetMapping("rest")
    public String rest() {
        return "Peace on you";
    }

    @GetMapping("pe-pagina")
    public String altiiCareVorSaTiIaCazarea() {
        return "PREA MULTI";
    }

    @GetMapping("sigur")
    public String sigur() {
        return "Date critice " + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
