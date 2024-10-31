package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AController {
  private final AService aService;
  private final Props props;
  private final UserRepository repo;

  @GetMapping
  public String hi() {
    boolean ePananCraciun = LocalDate.now().isBefore(props.b());
    return aService.f() + ePananCraciun;
  }
  @GetMapping("/users")
  public List<User> users() {
    return repo.findAll();
  }
  @GetMapping("/modify")
//  @Transactional // #1 autoflush dirty
  public void modify() {
    User user = repo.findById(1).get();
    user.setName("modificat");
    repo.save(user); // #2 manual save
  }


}
// controller sa cheme service
// copy-paste la LoggerAspect.java
// adnotati cu @Log metoda din service chemata din controller
// observati in consola logul

// vreau sa poti pune adnotarea si pe clasa, nu doar pe metoda
// daca nu merge, intreaba pe chatGPT chat.openai.com
// "cum pot sa fac un aspect sa intercepteze toate
// metodele dintr-o clasa care are o anumita adnotare
// sau adnotarea e pe metoda? in spring framework
//
// foloseste 1 singur @Around