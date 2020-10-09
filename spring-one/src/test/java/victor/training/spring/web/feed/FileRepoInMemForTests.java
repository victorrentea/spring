package victor.training.spring.web.feed;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Primary
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

   public void addTestFile(String fileName, String... lines) {
      fileContents.put(fileName, Arrays.asList(lines));
   }
   public void clearFiles() {
      fileContents.clear();
   }
}
