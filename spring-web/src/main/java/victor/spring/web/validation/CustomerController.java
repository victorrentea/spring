package victor.spring.web.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
public class CustomerController {
    @PostMapping("customers/{id}/draft")
    public String saveDraftCustomer(@PathVariable long id, @RequestBody @Valid CustomerDto dto) {
        String message = "Saved Draft " + dto.name;
        log.info(message);
        return message;
    }

    @PostMapping("customers/{id}/activate")
    public String activateCustomer(@PathVariable long id, @RequestBody @Validated(CustomerDto.ActivationUC.class) CustomerDto dto) {
        String message = "Activated " + dto.name;
        log.info(message);
        return message;
    }

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
