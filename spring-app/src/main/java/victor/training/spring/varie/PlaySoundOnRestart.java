package victor.training.spring.varie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class PlaySoundOnRestart {
  private static final String SOUND_FILE = "/start-low.wav";
//  private static final String SOUND_FILE = "/start.wav";
  @EventListener(ContextRefreshedEvent.class)
  public void playSound() {
    try {
      Clip clip = AudioSystem.getClip();
      InputStream inputStream = PlaySoundOnRestart.class.getResourceAsStream(SOUND_FILE);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
      clip.open(audioStream);
      clip.start();
      System.out.println("DONE");
    } catch (Exception e) {
      System.err.println("Cannot play" + e.getMessage());
    }
    log.info("Done!");
  }
}
