package victor.training.spring.life;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import victor.training.spring.ThreadUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Repository
public class CountryRepo {
	private final static Logger log = LoggerFactory.getLogger(CountryRepo.class);

//	@Cacheable("countries")
	public Map<String, String> loadCountryNamesAsMap(Locale locale) {
		// connect to database, get data. fake some latency
		log.debug("Loading country names for language: {} ...", locale );
		ThreadUtils.sleep(2000);
		log.debug("done");

		Map<String, String> map = new HashMap<>();
		switch (locale.getLanguage()) {
		case "en":
			map.put("RO", "Romania");
			map.put("ES", "Spain");
			break;
		case "fr":
			map.put("RO", "Roumanie");
			map.put("ES", "Espagne");
			break;
		default: throw new IllegalArgumentException("best practice");
		}
		return map; 
	}
	
}