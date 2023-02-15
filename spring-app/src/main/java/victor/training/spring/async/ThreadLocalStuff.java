package victor.training.spring.async;

import org.hibernate.context.internal.ThreadLocalSessionContext;

public class ThreadLocalStuff {
  // (@Transactional
  // SecurityContextHolder
  // @Scope(request)
  // sleuth traceID
  // Logback MDC %X{
  public static final ThreadLocal<String> THREAD_LOCAL_DATA = new ThreadLocal<>();
//
//  public void method() {
//    THREAD_LOCAL_DATA.set("stuff");
//    m();
//  }
//
//  private void m() {
//    String s = THREAD_LOCAL_DATA.get();
//  }
}
