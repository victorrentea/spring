package my.spring.playground.controller;

import lombok.RequiredArgsConstructor;
import my.spring.playground.domain.User;
import my.spring.playground.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements CommandLineRunner {
    private final UserRepo repo;
    @GetMapping
    public List<String> getAllUsers() {
        return repo.findAll().stream().map(User::getName).collect(toList());
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Cristi", "Maria", "Paul", "Mihai")
                .map(User::new)
                .forEach(repo::save);
    }
}
