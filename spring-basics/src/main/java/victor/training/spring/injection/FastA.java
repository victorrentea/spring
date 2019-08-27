package victor.training.spring.injection;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("altA")
@Primary
class FastA implements A {}
