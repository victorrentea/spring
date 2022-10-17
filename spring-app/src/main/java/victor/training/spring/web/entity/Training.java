package victor.training.spring.web.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data // not in prod
public class Training { // copilu
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private Date startDate;
	@ManyToOne
	private Teacher teacher;
	@ManyToOne // uneori Hibernate aduce si @..ToOne cu query-uri succesive
	private ProgrammingLanguage programmingLanguage;

	public Training() {
	}
	
	public Training(String name, Date startDate) {
		this.name = name;
		this.startDate = startDate;
	}
}
