package com.example.demo;

public class AService {
  private final ARepository repository;

  public AService(ARepository repository) {
    this.repository = repository;
  }

  public String hi() {
    return repository.hiJooq();
  }
}
