package victor.training.spring.web.controller.dto;


import reactor.core.publisher.Mono;
import victor.training.spring.web.entity.ContractType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TrainingDto {
	public interface ForCreateFlow {}
	public interface ForUpdateFlow {}
	public Long id;
	@NotNull
	@Size(min = 3, max = 50, groups = ForCreateFlow.class)
	public String name;
	public ContractType level;
	public Long teacherId;
	public String teacherBio;
	public Long languageId;
	public String teacherName;
	public String startDate;
	public String description;
}
