package victor.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeepController {
    @Autowired
    private DeepService deepService;

    @GetMapping("deep")
    public String deep() {
        return deepService.securedMethod(1L);
    }
}


@Component
class DeepService {
    @PreAuthorize("hasRole('USER') && @countryProtector.allow(principal)")
    public String securedMethod(long countryId) {
        return "a";
    }
}
@Component
class CountryProtector {
    public boolean allow(User principal) {
        System.out.println("Validate: " + principal);
        return true;
    }
}


