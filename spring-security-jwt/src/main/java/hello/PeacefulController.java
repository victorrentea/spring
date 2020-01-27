package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PeacefulController {
    @Autowired
    PeacefulRepository repo;

    // in the resource server (your backend)
    @GetMapping("/rest")
    public String rest() {
        return "Peace on you " + repo.save();
    }
}

