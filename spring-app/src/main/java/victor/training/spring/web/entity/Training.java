package victor.training.spring.web.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data // not in prod
public class Training {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private LocalDate startDate;
	@ManyToOne
	private Teacher teacher;
	@Enumerated(EnumType.STRING)
	private ProgrammingLanguage programmingLanguage;
	@Version
	private Long version;

	public Training() {
	}
	
	public Training(String name, LocalDate startDate) {
		this.name = name;
		this.startDate = startDate;
	}
}
