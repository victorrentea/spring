package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.events.Event.ID;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ProfilePlay {

   private final IDep dep;

   @PostConstruct
   public void print() {
      System.out.println("Dep = " + dep.getClass());
   }
}


interface IDep {

}

@Component
class DepImpl implements IDep {

}

@Component
@Primary
@Profile("dummy")
class DepDummy implements IDep {

}