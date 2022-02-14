package victor.training.spring.web.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@Entity
public class Training {
	@Id
	@GeneratedValue
	private Long id;
//	@Size(min = 3)
	private String name;
	private String description;
	private Date startDate;
	@ManyToOne
	private Teacher teacher;
	@ManyToMany
	private List<Tag> tags = new ArrayList<>();

	
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

	public List<Tag> getTags() {
		return tags;
	}

	@Override
	public String toString() {
		return "Training{" +
				 "id=" + id +
				 ", name='" + name + '\'' +
				 ", description='" + description + '\'' +
				 ", startDate=" + startDate +
				 '}';
	}

	public void addTags(Tag... newTags) {
		tags.addAll(asList(newTags));
	}
}
