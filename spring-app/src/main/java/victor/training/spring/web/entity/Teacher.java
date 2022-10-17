package victor.training.spring.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Teacher {
	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private ContractType contractType;

	private String name;

	@OneToMany(mappedBy = "teacher")
	private List<Training> trainings = new ArrayList<>();
	// hibernate va pune un PersistentBag/Set pe post de List

	protected Teacher() {}
	
	public Teacher(String name) {
		this.name = name;
	}

}
