package victor.training.spring.varie.validationgroups;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Mini-aplicatie doar pentru testul de persistenta: @DataJpaTest cauta cea mai apropiata
 * @SpringBootConfiguration urcand pe pachete, deci o gaseste pe asta si NU porneste
 * intreaga aplicatie (security, feign, actuator...).
 * Entity scan + repository scan pornesc din pachetul acestei clase.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
public class ValidationGroupsTestApp {
}
