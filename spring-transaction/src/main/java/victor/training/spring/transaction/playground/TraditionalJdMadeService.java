package victor.training.spring.transaction.playground;

import org.springframework.transaction.annotation.Transactional;

//public class TraditionalJdMadeService {
//
//    public void readStuff() {
//        // SELECT does NOT need a TX
//    }
//@Transactional
//    public void method() {
//        repo.find
//        restTemplate.getForObject("apicall") // network call inside a @Transactional method
//        //leads to JDBC connection starvation issue (blocking one of the 20 conn in the JBBC connectoon pool)
//    // this can monitored using connection acquisition time
//        webClient.getForObject("apicall")
//        repo.save// INSERT
//        repo.save // UPDATE
//    }
//}
