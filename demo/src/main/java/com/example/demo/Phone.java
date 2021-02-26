package com.example.demo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlType
public class Phone {
   @XmlElement
   private String value;
   @XmlAttribute
   private String type;
}
