package victor.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeepController {
    @Autowired
    private DeepService deepService;

    @GetMapping("deep")
    public String deep() {
        return deepService.securedMethod(1L);
    }
    @GetMapping("alt") // alt?param=true/false
    public String altaCale(@RequestParam boolean param) {
        if (param) {
            return deepService.securedMethod(1L);
        } else {
            return "No need to call securedMethod";
        }
    }
}
@Component
class DeepService {
    @PreAuthorize("@countryAccessValidator.canAccessCountry(#countryId) && hasRole('ADMIN')")
    public String securedMethod(long countryId) {
//        if (useru curent are drpet pe countrcyId)
        return "a";
    }
}

@Component
class CountryAccessValidator {
    @Autowired
    private UserRepo repo;
    public boolean canAccessCountry(long countryId) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println("authentication: " + authentication);
        System.out.println("authentication.class: " + authentication.getClass());
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        System.out.println("principal: " + token.getPrincipal());
        System.out.println("principal.class: " + token.getPrincipal().getClass());

        User user = (User) token.getPrincipal();
        String username = user.getUsername();

        // TODO ideal: user.getAccessibleCountryIds().contains(countryId);

        boolean b = repo.userHasAccessToCountry(username, countryId);

        System.out.println("Chem validater programatica pt country " + countryId);
        return b;
    }
}

@Repository
class UserRepo {
    public boolean userHasAccessToCountry(String username, long countryId) {
        return true;// QUERY HERE
    }
}