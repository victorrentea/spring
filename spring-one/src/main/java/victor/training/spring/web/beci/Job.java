package victor.training.spring.web.beci;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Component
public class Job {

   @Value("${beci.job.in.folder}")
   private File inFolder;

   @PostConstruct
   public void checkInFolderExists() {
      if (!inFolder.isDirectory()) {
         throw new IllegalArgumentException("Nu folderu! " + inFolder.getAbsolutePath());
      }
   }

   @Scheduled(fixedRateString ="${in.folder.purger.rate.millis}")
   public void stergetTot() {
      log.info("La poarta la Stefan, oare cine bate?");
      for (File file : inFolder.listFiles()) {
         file.delete();
         log.info("Am sters " + file.getName());
      }
   }

}
