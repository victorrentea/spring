package victor.training.spring.first;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

record ReservationRequest(
    @NotNull @Size(min = 3) String name,
    @NotNull String phone
) {
//  ReservationRequest{
//    Objects.requireNonNull(name);
//  if (size < )
//    Objects.requireNonNull(phone, "phone");
//  }
}

@RestController
public class RestApi {
  private final Repo repo;

  public RestApi(Repo repo) {
    this.repo = repo;
  }

  @Operation(summary = "Create a new reservation")
  @PostMapping // should this be IDEMPOTENT? NO = "create". 2 request = 2 creates
  // to make this idempotent, include a unique identifier in the request
  // for example a id=UUID.randomUUID() on the client side < "client-side generate ID" pattern
  // id here acts as an "idempotency key"
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

  @GetMapping("{id}") // should this be IDEMPOTENT? YES = "read"
  public String get(@PathVariable long id) {
    return repo.findById(id).get();
  }

  // idempotent = the same request can be sent multiple times without changing the outcome on server
  // synonim to "retryable"

//  @PutMapping("{id}") // should this be IDEMPOTENT? YES = "overwrite", "set"
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
