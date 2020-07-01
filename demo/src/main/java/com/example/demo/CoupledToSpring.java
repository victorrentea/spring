package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoupledToSpring {
   private final MyRepo myRepo;
}


@Repository
class MyRepo {

}