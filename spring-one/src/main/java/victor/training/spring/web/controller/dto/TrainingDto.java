package victor.training.spring.web.controller.dto;

import victor.training.spring.web.domain.Training;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@Valida
//@interface ValidTeacherId {
//
//}
//
//class ValidTeacherId implements Validator {
//
//}

public class TrainingDto {
	public Long id;
	@Size(min = 2)
	@NotNull
	public String name;
//	@ValidTeacherId
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;
}
