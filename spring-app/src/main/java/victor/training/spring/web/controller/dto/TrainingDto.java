package victor.training.spring.web.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import victor.training.spring.web.entity.ContractType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TrainingDto {
	public Long id;
	@Size(min = 3, max = 50, message = "{customer.name.length}")
	public String name;
	public ContractType level;
	public Long teacherId;
	public String teacherBio;
	@NotNull
	public Long languageId;
	public String teacherName;
	public String startDate;
	public String description;
}
