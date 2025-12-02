package victor.training.spring.ai;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class VictorTrainingCatalog {


  @Tool(description = "Get the catalog of trainings Victor can teach.")
  public String getVictorTrainingCatalog(@ToolParam( description =  "Programming Language") String language) {
    return "Clean Code\nSpring\nJPA\nClean Architecture\nMicroservices\nEvent-Driven Architectures";
  }

}
