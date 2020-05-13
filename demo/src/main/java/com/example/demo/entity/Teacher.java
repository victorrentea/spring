package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teacher {
	@Id
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
