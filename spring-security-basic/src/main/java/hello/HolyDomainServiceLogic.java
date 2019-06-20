package hello;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class HolyDomainServiceLogic {
    @PreAuthorize("hasRole('ADMIN')")
    //controlor.verifica(principal) &&
    public String pisica() {
        return "Birmaneza";
    }
}


@Component
class Controlor {
    public boolean verifica(Principal principal) {
        System.out.println("Verific pe " + principal.getName());
        return true;
    }
}