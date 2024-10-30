package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.lib.X;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class Y {
  // evita :default pt ca vrei in properties/yaml sa le gasesti pe toate
//  @Value("${props.gateNumber:85}")
  @Value("${props.gate}")
  private final int gate; // replace with injected Props
//  private final LocalDate gate; // #fun 🤓
  // alte tipuri: File/UUID/URL/Resource/Class/Pattern/Charset/TimeZone

  private final MailService mailService; // polymorphic injection
  @Lazy // ce e aia?!
  private final X x;
  //injectia dep (camp/ctor) se face DOAR o data, la inceput

  public int logic() {
    System.out.println(
        gate + " Oare ce x mi-a injectat springu? "
         + x.getClass());
    mailService.sendEmail("Go to gate " + gate);
    System.out.println(x.f());
    System.out.println(
        "Oare ce x mi-a injectat springu? "
         + x.getClass());
    return 1;
  }
}
