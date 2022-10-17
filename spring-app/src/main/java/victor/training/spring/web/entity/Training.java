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
	private Teacher teacher; // +1 query cand aduc 1 training

//	@ManyToOne // uneori Hibernate aduce si @..ToOne cu query-uri succesive
//	private ProgrammingLanguage programmingLanguage;  // +1 query cand aduc 1 training


	// la incarcarea FE, FE face un GET /api/countries -> pe care apoi le tine intr-un var
	private Long programmingLanguageId; // ! lasi si FK in DB,

	// programmingLanguage = date stattice, referentiale, ce se modifica doar la deploy cu scripturi.
	// foarte des nu ai ecran de editare ale lor.

	public Training() {
	}
	
	public Training(String name, Date startDate) {
		this.name = name;
		this.startDate = startDate;
	}
}
