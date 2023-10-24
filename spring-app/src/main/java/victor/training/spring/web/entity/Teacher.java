package victor.training.spring.web.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;

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

//	@Create
//	@LastModifiedBy
//	private String lastModifiedBy;


	protected Teacher() {}
	public Teacher(Long id) {
		this.id = id;
	}

	public Teacher(String name) {
		this.name = name;
	}

}
