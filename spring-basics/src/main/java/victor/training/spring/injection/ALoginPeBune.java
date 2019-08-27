package victor.training.spring.injection;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("!masina.mea")
class ALoginPeBune implements ALogin {
    //hackuie
}
