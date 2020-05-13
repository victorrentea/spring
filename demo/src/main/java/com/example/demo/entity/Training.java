package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Entity
public class Training {
	@Id
	private Long id;
	private String name;
	@Lob
	private String description;
//	@Temporal(TemporalType.DATE) // inutil
	private LocalDate startDate;
	@ManyToOne
	private Teacher teacher;

	
	public Training() {
	}
	
	public Training(String name, String description, LocalDate startDate, Teacher teacher) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		setTeacher(teacher);
	}

	public Training setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Optional<LocalDate> getStartDate() {
		return ofNullable(startDate);
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

//	public Optional<Teacher> getTeacher() {
//		return ofNullable(teacher);
//	}
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		Objects.requireNonNull(teacher);
		this.teacher = teacher;
	}
}
