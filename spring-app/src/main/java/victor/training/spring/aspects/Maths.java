package victor.training.spring.aspects;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
//@LoggedMethod
    /*final*/
public class Maths { // crapa la startup

  //    @Secured("ROLE_ADMIN") sau @Transactional  NU MERG!!!!
  @Cacheable("sums")
//  @CacheEvict / @CachePut
  /*final sau static*/
  public int sum(int a, int b) { // ignora metoda
    System.out.println("Thinking...🤔 " + a + " + " + b);
    return a + b;
  }

  public int product(int a, int b) {
    int r = 0;
    for (int i = 0; i < b; i++) {
      r = sum(r, a); // 👑 apelul asta nu a fost interceptat de proxy. E un apel local.
    }
    return r;
  }
}
