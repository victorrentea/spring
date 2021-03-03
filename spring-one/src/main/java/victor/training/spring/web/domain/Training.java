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
	private String createdByUsername;


	public Training() {
	}
	
	public Training(String name, String description, Date startDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
	}
	public Training(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Training{" +
				 "id=" + id +
				 ", name='" + name + '\'' +
				 '}';
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


	public Training setTeacher(Teacher teacher) {
		this.teacher = teacher;
		return this;
	}

	public Training createdBy(String createdByUsername) {
		this. createdByUsername = createdByUsername;
		return this;
	}
}
