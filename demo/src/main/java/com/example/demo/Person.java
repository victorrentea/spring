package com.example.demo;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "person")
public class Person {
   @XmlElement
   private String name;
   @XmlElement
   private String id;
   @XmlElement
   private List<Phone> phones = new ArrayList<>();
}
