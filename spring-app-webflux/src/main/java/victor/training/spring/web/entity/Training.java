package victor.training.spring.web.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Date;

@Data // not in prod
public class Training {
	@Id
	private Long id;
	private String name;
	private String description;
	private LocalDate startDate;
	private Long teacherId;
	private Long programmingLanguageId;

	public Training() {
	}
	
	public Training(String name, Date startDate) {
		this.name = name;
		this.startDate = LocalDate.of(2020,02,02);//startDate;
	}
}
