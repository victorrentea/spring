package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

public class Cyclism {
}

@Service
class Za1 {
   @Autowired
   private Za2 za2;
   @PostConstruct
   public void m() {
      System.out.println("Za12:" + za2);
   }
}

@Service
class Za2  {
   @Autowired
   private Za1 za1;
   @PostConstruct
   public void m() {
      System.out.println("Za1:" + za1);
   }
}
