package com.example.demo.entity;

import javax.persistence.*;

@Entity
//@Table(
//		uniqueConstraints = @UniqueConstraint(name="UK_NAME",columnNames = "NAME"),
//		indexes = @Index()
//)
public class Teacher {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Teacher setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
