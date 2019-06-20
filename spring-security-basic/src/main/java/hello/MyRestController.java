package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@RestController
public class MyRestController {
    @PostMapping("transfer")
    public String transferMoney(@RequestParam String accountNo, @RequestParam String amount) {
        log.debug("Transferring EUR {} to account {}", amount, accountNo);
        return "CAT Image!";
    }

    @Autowired
    private HolyDomainServiceLogic service;

    @GetMapping("pisici")
//    @Secured("ROLE_ADMIN")
//    @PreAuthorize("hasRole('ADMIN')")
    public String pisica() {
        return service.pisica();
    }


    @GetMapping("locale")
    public String locale(HttpServletRequest request) {

        return request.getLocale().toString();
    }

    @GetMapping("date/{date}")
    public String convertDate(@PathVariable Date date) {
        return "Anul Boului [CN]: " + date;
    }

}

@Slf4j
@RestControllerAdvice
class Global {

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @ExceptionHandler(Throwable.class)
    public String handle(Throwable t) {
        log.error("Ceva rau + "+t ,t);
        return "ntz";
    }
}
