package victor.training.spring.first;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class Y {
  private final MailService mailService;// numele punctului de injectie = @Qualifier ("name")
  // daca numele identifica un bean -> app porneste ✅
  // daca are 2 canditati -> crapa la startup ❌
  private final Props props;

//  private final Map<string(bean-name),MailService> toateImpl;
  private final List<MailService> toateImpl; // chain of responsability design pattern
  // use-case: vrei sa notifici userul pe toate canalele disponibile
//  List<NotificationInter{notify(message,user)}>  for (impl:toateImpl) impl.notify(message,user);

  // use-case: vrei sa aplici promotii pe ShoppingCart
  // use-case: vrei sa validezi criterii de eligibiliate pentru credite

  // poate iesi o varza mare daca ai cuplare temporala intre procesoare. unele chiar au

  // @Autowired private  doar in @Test

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
    System.out.println(toateImpl);

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
