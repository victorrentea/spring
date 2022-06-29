package victor.training.spring.web.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data // not in prod
public class Training {
	@Id
	@GeneratedValue
	private Long id;
	@Size(min = 3, max = 50)
	@Column(nullable = false) // NOT NULL in DB
	private String name;
	@NotNull
	private String description;
	private Date startDate;
	@ManyToOne
	private Teacher teacher;
	@ManyToOne
	private ProgrammingLanguage programmingLanguage;

	public Training() {
	}
	
	public Training(String name, Date startDate) {
		this.name = name;
		this.startDate = startDate;
	}
}
