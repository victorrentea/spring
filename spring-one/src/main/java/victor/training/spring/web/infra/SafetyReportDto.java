package victor.training.spring.web.infra;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SafetyReportDto {
   private List<SafetyEntryDto> entries = new ArrayList<>();
}
