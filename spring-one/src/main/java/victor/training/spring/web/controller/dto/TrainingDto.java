package victor.training.spring.web.controller.dto;

import java.io.Serializable;

public class TrainingDto implements Serializable {
	public Long id;
	public String name; //
	public Long teacherId; //
	public String teacherName;
	public String startDate; //
	public String description;
}
