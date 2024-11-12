package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AController {
  private final AService aService;

  record Dto(
      @Schema(description = "A field")
      @NotNull @Size(max = 20) String a,
      String b) {
  }

  @Operation(description = "Documentatie ca sa nu ma suni")
  @GetMapping // http://localhost:8080
  public Dto hello() {
    System.out.println("Chem pe " + aService.getClass());
    return new Dto(aService.metodaSmechera(), "b");
  }

  @GetMapping("/export")
  public void export() {
    writeExportFile();
  }

  @SneakyThrows
  private static void writeExportFile()  {
    Thread.sleep(10000);
  }
}
