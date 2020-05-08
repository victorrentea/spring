package com.example.demo.entity;

public class Teacher {
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

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

}
