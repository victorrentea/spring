package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AController {
  private final AService aService;

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


}
