//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import javax.transaction.Transactional;
//
//@RestController
//@RequiredArgsConstructor
//public class WonderfulController {
//
//    private final WonderFullService wonderFullService;
//
//    @GetMapping("wonder")
//    public String getWonder() {
//        wonderFullService.stuff();
//        return "wonder";
//    }
//}
//
//@Service
//@RequiredArgsConstructor
//class WonderFullService {
//
//    private final DeeperStuff deeperStuff;
//
//    public void stuff() {
//        //do stuff that takes long and can fail safely (fetch stuff on network, ....)
//        deeperStuff.mustBeCallOutsideTransaction();
//        //do stuff that can be considered as a unit of work (persist the fetched stuff somewhere, ....)
//        transactionalStuff();
//    }
//
//    @Transactional
//    public void transactionalStuff() {
//        deeperStuff.mustBeCallInTransaction();
//    }
//}
//
//@Component
//interface DeeperStuff {
//
//    void mustBeCallInTransaction();
//
//    void mustBeCallOutsideTransaction();
//}