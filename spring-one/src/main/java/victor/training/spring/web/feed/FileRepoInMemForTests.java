package victor.training.spring.web.feed;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Profile("test")
@Component
public class FileRepoInMemForTests implements IFileRepo{
   private final Map<String, List<String>> fileContents = new HashMap<>();
   @Override
   public Collection<String> getFileNames() {
      return fileContents.keySet();
   }

   @Override
   public Stream<String> openFile(String fileName) {
      return fileContents.get(fileName).stream();
   }

   public Map<String, List<String>> getFileContents() {
      return fileContents;
   }
}
