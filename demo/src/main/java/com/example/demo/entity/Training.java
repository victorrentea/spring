package com.example.demo.entity;

import java.time.LocalDate;
import java.util.Date;

public class Training {
	private Long id;
	private String name;
	private String description;
	private LocalDate startDate;
	private Teacher teacher;

	
	public Training() {
	}
	
	public Training(String name, String description, LocalDate startDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
	}

	public Training setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getId() {
		return id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final LocalDate getStartDate() {
		return startDate;
	}

	public final void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public final Teacher getTeacher() {
		return teacher;
	}

	public final void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


}
