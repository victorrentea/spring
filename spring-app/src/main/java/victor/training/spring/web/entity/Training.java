package victor.training.spring.web.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data // not in prod
public class Training {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@Column(nullable = false)
	private String name;
	private String description;
	private Date startDate;
	@ManyToOne
	private Teacher teacher;
	@ManyToOne
	private ProgrammingLanguage programmingLanguage;
	private LocalDate creationDate = LocalDate.now();

	public Training() {
	}
	
	public Training(String name, Date startDate) {
		this.name = name;
		this.startDate = startDate;
	}
}
