package victor.training.spring.web.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;


@Entity
public class Training {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private Date startDate;
	@ManyToOne
	private Teacher teacher;

	
	public Training() {
	}
	
	public Training(String name, String description, Date startDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


}
