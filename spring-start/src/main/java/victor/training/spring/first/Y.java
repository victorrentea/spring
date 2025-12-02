package victor.training.spring.first;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Y {
  @Qualifier("mailServiceImpl")
  private final MailService mailService;
  private final Props props;

  // - unele teste unitare solitare (de le faci si cu AI-ul) @InjectMocks => nu-ti pasa
  // - unele teste unitare sociale destepte vor sa testeze 2+ clase inconjurate de mockuri => new Y
//  public Y(MailService mailService, Props props) {
//    this.mailService = mailService;
//    this.props = props;
//  }

  // instictiv devii be pursange urasc var.
  // FE il iubesc
  // au fortat in BE var peste tot cu sonar!
  //  => efect toate variabilele locale aveau nume INGERESTI. lungi. frumoase. explicative. sa compenseze!

  public int logic() {
    final var ss = "a";
    val ss2 = "a";
    mailService.sendEmail("Go to gate " + props.gate());
//  new EntityMeu().setA("a").setB("b ") // #musthave in @Test
    return 1;
  }
}
@Data
class EntityMeu {
  private String a,b,c;
}
