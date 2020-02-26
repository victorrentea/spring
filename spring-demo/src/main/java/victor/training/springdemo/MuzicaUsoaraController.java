package victor.training.springdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MuzicaUsoaraController {
    @GetMapping("albume")
    public List<String> getAlbume(@RequestParam(name = "name", required = false) String namePart) {
        System.out.println(namePart);
        return Arrays.asList("Iasomie in Metrou", "Elektrik ViB");
    }

    @GetMapping("albume/{id}")
    public String getAlbumById(@PathVariable long id) {
        return "Album " + id;
    }
}
