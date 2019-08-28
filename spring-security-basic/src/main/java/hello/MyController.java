package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class MyController {
    @PostMapping("www")
    public String helloWeb(@RequestBody String body) {
        return "Hello Web " + body;
    }
    @GetMapping("get")
    public String helloWeb() {
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

    public String convertDate(Date date) {
        return "Anul Boului [CN]";
    }

//    @InitBinder
//    public void dateBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//    }
}
