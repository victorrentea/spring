package victor.training.spring.life;

import java.util.Locale;

public interface ILabelService {
   //	@PostConstruct
   void load(Locale locale);

   String getCountryName(String iso2Code);
}
