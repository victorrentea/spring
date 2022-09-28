package victor.training.spring.web.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Teacher {
	@Id
	private Long id;

	private ContractType contractType;

	private String name;

	protected Teacher() {}
	
	public Teacher(String name) {
		this.name = name;
	}

}
