package victor.training.spring.injection.p2;

import org.springframework.stereotype.Service;
import victor.training.spring.injection.Fast;
import victor.training.spring.injection.I;

@Service("altA")
@Fast
class A implements I {}
