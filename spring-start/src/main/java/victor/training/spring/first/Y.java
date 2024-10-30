package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.lib.X;

@Slf4j
@Service
@RequiredArgsConstructor
public class Y {
  @Value("${props.gate}")
  private Integer gate; // replace with injected Props

  private final MailService mailService; // polymorphic injection
  @Lazy // ce e aia?!
  private final X x;
  //injectia dep (camp/ctor) se face DOAR o data, la inceput

  public int logic() {
    System.out.println(
        "Oare ce x mi-a injectat springu? "
         + x.getClass());
    mailService.sendEmail("Go to gate " + gate);
    System.out.println(x.f());
    System.out.println(
        "Oare ce x mi-a injectat springu? "
         + x.getClass());
    return 1;
  }
}
