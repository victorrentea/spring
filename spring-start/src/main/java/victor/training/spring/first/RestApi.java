package victor.training.spring.first;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

record ReservationRequest(
    @NotNull String name,
    @NotNull String phone
) {
//  ReservationRequest{
//    Objects.requireNonNull(name);
//    Objects.requireNonNull(phone, "phone");
//  }
}

@RestController
public class RestApi {
  private final Repo repo;

  public RestApi(Repo repo) {
    this.repo = repo;
  }

  @PostMapping
  public void create(@RequestBody @Validated ReservationRequest orderRequest) {
    System.out.println("Order created: " + orderRequest);
  }

//  @GetMapping("{id}")
//  public ResponseEntity<String> get(@PathVariable long id) {
//    // do not do this: polluting your code with presentation logic
//    try {
//      return ResponseEntity.ok(repo.findById(id).get()); //200
//    } catch (NoSuchElementException e) {
//      return ResponseEntity.status(404).body("nada"); //404
//    }
//  }

  @GetMapping("{id}")
  public String get(@PathVariable long id) {
    return repo.findById(id).get();
  }

//  @PutMapping("{id}")
//  public void update( @PathVariable long id, @RequestBody String newContent) {
//    repo.findById(id).if
//  }
}

@Repository
class Repo {
  public Optional<String> findById(long id) {
//    return Optional.of("Hello");
    return Optional.empty();
  }
}
