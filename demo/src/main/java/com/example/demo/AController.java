package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AController {
  private final AService aService;
  private final UserRepository userRepository;

  record Dto(
      @Schema(description = "A field")
      @NotNull @Size(max = 20)
      String a,
      String b) {
  }

  @Operation(description = "Documentatie ca sa nu ma suni")
  @GetMapping // http://localhost:8080
  public Dto hello() {
    System.out.println("Chem pe " + aService.getClass());
    return new Dto(aService.metodaSmechera(), "b");
  }

  @GetMapping("/export")
  public String export() throws IOException {
    var id = UUID.randomUUID().toString();
    aService.generate(id);
    return id;
  }
  @GetMapping("/users") // NICIODATA nu intorci @ENtity pe REST response
  @Cacheable("useri") // declarativ cu AOP proxy.
  public List<User> findAll() {
//    Object o = cacheManager.getCache("useri").get("x").get(); // sau programatic
    System.out.println("In metoda");
    return userRepository.findAll();
  }

  @GetMapping("/evict-cache")
  @CacheEvict("useri")
  public void evictCache() {
  }

  private final CacheManager cacheManager;


}
