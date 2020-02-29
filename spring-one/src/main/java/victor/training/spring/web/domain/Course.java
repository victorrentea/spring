package victor.training.spring.web.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Course {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private Date startDate;
	@ManyToOne
	private Teacher teacher;

	
	public Course() {
	}
	
	public Course(String name, String description, Date startDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
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

	public final Date getStartDate() {
		return startDate;
	}

	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public final Teacher getTeacher() {
		return teacher;
	}

	public final void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


}
