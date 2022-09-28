package victor.training.spring.web.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data // not in prod
public class Training {
	@Id
	private Long id;
	private String name;
	private String description;
	private Date startDate;
	private Long teacherId;
	private Long programmingLanguageId;

	public Training() {
	}
	
	public Training(String name, Date startDate) {
		this.name = name;
		this.startDate = startDate;
	}
}
