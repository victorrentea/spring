package victor.training.spring.web.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
// @Data n-ai voie!!, ci doar :
// - hashCode/equals pe JPA nu are sens cand ai PK
// - toString pe toate campurile poate fi periculos pt ca poate intra in ciclu infinit
@Setter
@Getter
public class Teacher {
	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private ContractType contractType;

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
