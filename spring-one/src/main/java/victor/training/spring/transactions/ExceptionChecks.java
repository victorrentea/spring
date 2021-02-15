package victor.training.spring.transactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExceptionChecks {
   static Validator validator = new Validator();
   public static void main(String[] args) {
      try {
         String url = "asdsfsaSAD";
         validator.validate(url);

//         url.startsWith("http://");

//         try {
//            System.out.println("try do network call");
//         } catch (HostNotFoundException e) {
//            //ia sa vedem, pe hostul de backup merge?
//         }

//         new SimpleDateFormat("yyyy-mm-dd").parse("2020-01-01");

         System.out.println("networkCall to  " + url);
      } catch (ValidationException e) {
//         repo.save(new Error());
         throw new RuntimeException("Param ilegal", e);
//         e.printStackTrace();
      }
   }
}


class Validator {

   void validate(String ceva) throws ValidationException {
      if (ceva.length() > 20) {
         throw new ValidationException(/*ErrorCode.TOO_LONG*/);
      }
      if (!Character.isUpperCase(ceva.charAt(0)) ){
         throw new ValidationException(/*ErrorCode.NOT_STARTING_WITH_UPPER*/);
      }
   }
}
class ValidationException extends  Exception{
   private static final Logger log = LoggerFactory.getLogger(ValidationException.class);


   public ValidationException() {
//      log.
   }
}