package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class MyService {
   void met() {
      if (true) {
         throw new IllegalArgumentException();
      }
   }
}