package victor.training.spring.varie.advancedvalidation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
    public String activateCustomer(@PathVariable long id, @RequestBody @Valid  CustomerDto dto) {
        String message = "Activated " + dto.name;
        log.info(message);
        return message;
    }
}
