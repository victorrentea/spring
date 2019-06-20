package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class MyController {

    @GetMapping("transfer")
    @ResponseBody
    public String transferMoney(@RequestParam String accountNo, @RequestParam String amount) {
        log.debug("Transferring EUR {} to account {}", amount, accountNo);
        return "CAT Image!";
    }

    @GetMapping("pisici")
    @ResponseBody
    public String pisica() {
        return "Pisica";
    }










    public String redirect() {
//        redirect to https://myhost.com/some/arbitrary/path
        return "??";
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
