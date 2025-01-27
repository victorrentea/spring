package victor.training.spring.web.entity;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
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

	@Size(min = 3, max = 100) // automat validate la INSERT/UPDATE
	private String name;
	@OneToMany(mappedBy = "teacher")
	private List<Training> trainings = new ArrayList<>();

	protected Teacher() {}
	public Teacher(Long id) {
		this.id = id;
	}

	public Teacher(String name) {
		this.name = name;
	}

}
