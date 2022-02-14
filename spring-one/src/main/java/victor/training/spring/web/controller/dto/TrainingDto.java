package victor.training.spring.web.controller.dto;


import victor.training.spring.web.controller.dto.TrainingDto.ValidationGroup.CreateFlow;
import victor.training.spring.web.controller.dto.TrainingDto.ValidationGroup.UpdateFlow;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class TrainingDto {
	public interface ValidationGroup {
		public interface CreateFlow {}
		public interface UpdateFlow {}
	}
	public Long id;
	@NotNull
//	@Pattern()
	@Size(min = 5, max = 50, groups = CreateFlow.class)
	@Size(min = 3, max = 50, groups = UpdateFlow.class)
//	@ValidTrainingName
	public String name;
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;
}

//class SubClass extends TrainingDto{
//	@Pattern()
//	public String getName() {
//		return name;
//	}
//}

// suppose that on create,
// the training should have a name greater than 5
// but on update we could give only 3 characters

// "DRAFT state"