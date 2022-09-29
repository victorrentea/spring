package victor.training.spring.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
@Service
public class SomeService {
    @Autowired
    ThirdPartyClass thirdPartyClass;

    @PostConstruct
    public void init() {
        thirdPartyClass.useful();
    }
}
