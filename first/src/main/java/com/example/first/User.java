package com.example.first;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
   @Id
   Long id;
   String name;
}
