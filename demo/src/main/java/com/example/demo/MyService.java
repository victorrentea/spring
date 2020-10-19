package com.example.demo;

import com.example.demo.MyException.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class MyService {
   void met() {
      if (true) {
//         throw new IllegalArgumentException();
         throw new MyException(ErrorCode.BUBA1);
      }
   }
}