package victor.training.spring.web.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToMany(mappedBy = "teacher")
	private List<Training> trainings = new ArrayList<Training>();

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

	public final List<Training> getTrainings() {
		return trainings;
	}

	public final void setTrainings(List<Training> cours) {
		this.trainings = cours;
	}

}
