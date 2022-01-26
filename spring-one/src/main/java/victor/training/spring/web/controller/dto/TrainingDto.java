package victor.training.spring.web.controller.dto;


import victor.training.spring.web.controller.dto.State.DRAFT;

import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface AccessibleFor {

}

public class TrainingDto {
	public Long id;
	@Size(min = 3, max = 50, groups = DRAFT.class)
//	@ValidTrainingName
	public String name;
	public Long teacherId;
	public String teacherName;
//	@AccessibleFor("ADMIN") // >>>> implement an @Aspect around all methods of a @RestController and post-process the returned value
	// traversing the graph of objects using reflection and NULL-in the fields that Should NOT be sent

	///https://www.youtube.com/watch?v=HbbvyZh3IZo&list=PLggcOULvfLL_MfFS_O0MKQ5W_6oWWbIw5&index=5&t=1s
	public String startDate;
	public String description;

//	@AccessibleFor("ADMIN")
//	public List<String> tags;
}
