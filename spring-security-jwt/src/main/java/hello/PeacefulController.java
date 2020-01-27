package hello;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
public class PeacefulController {
@Autowired Repo repo;
    // in the resource server (your backend)
    @GetMapping("/rest")
    public String rest() {
        return "Peace on you " + repo.save();
    }
}

@Component
class Repo {
    public String save() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("INSERT INTO Xbla bla SET LAST_MODIFIED_BY=" + userName);
        return userName;
    }
}
