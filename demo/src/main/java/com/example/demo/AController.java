package com.example.demo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AController {
  private final AService service;

  public AController(AService service) {
    this.service = service;
  }

  @GetMapping
  public String hi() {
    return service.hi();
  }

  @GetMapping("cached")
  @Cacheable("cached")
  public String cached(@RequestParam(defaultValue = "a") String q) {
    return "Hello" + System.currentTimeMillis();
  }


  @GetMapping("/propagation")
  public void propagation() {
    service.propagation();
  }
//  @GetMapping("/propagation")
//   DONT!!!
//  public ResponseEntity<String> propagation() {
//    try {
//      service.propagation();
//    }catch (Exception e) {
//      return ResponseEntity.internalServerError()
//          .body(e.getMessage());
//    }
//    return ResponseEntity.ok("👍");
//  }


  // http://localhost:8080/submit?work=something
  @GetMapping("submit")
  public String start(@RequestParam String work) {
    String taskId = UUID.randomUUID().toString();
    service.startTaskAsync(taskId, work);
    return taskId;
  }
  @GetMapping("result/{taskId}")
  public String result(@PathVariable String taskId) {
    return service.getResult(taskId);
  }

//  @PostMapping
  @GetMapping("/create")
  public int create(
      @RequestParam(defaultValue = "John Doe") String name) throws Exception {
    //return service.create(name);
    //service.createAtomic(name);
    //return service.createAtomic(name);
    return service.createReadOnly(name);
//    return service.createThrowingRuntimeException(name);
//    return service.createThrowingCheckedException(name);
  }
}
