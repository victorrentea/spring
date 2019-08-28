package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
class AltServiciu {

    @PreAuthorize("hasRole('ADMIN') ") //TODO and authen.canProceed()
    public void metodaCritica() {
    //if (!  user.countryId in currentUser.tari )
    //     throw N-ai voie
    System.out.println("CRITIC FRATE");
    }
}

@Service
class Authen {
    public boolean canProceed() {
        System.out.println("Can proceeed ?");
        return true;
    }
}
@Slf4j
@RestController
public class MyController {

    @Autowired
    private AltServiciu altServiciu;
    @PostMapping("www")
    public String helloWeb(@RequestBody String body) {
        return "Hello Web " + body;
    }
    @GetMapping("get")
    public String helloWeb() {
        altServiciu.metodaCritica();
        return "Hello Web !";
    }

    static class TransferDto {
        public String accountNo;
        public String amount;
    }

    @PostMapping("transfer")
    public String transferMoney(
            @RequestBody TransferDto dto) {
        log.debug("Transferring EUR {} to account {}",
                dto.amount,
                dto.accountNo);
        return "CAT Image!";
    }



    public String locale(HttpServletRequest request) {
        return "Moldoveneste";
    }

    //       /date/2019-02-01
    @GetMapping("date/{date}")
    public String convertDate(@PathVariable Date date) {
        return "Anul Boului [CN] : " + date;
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
