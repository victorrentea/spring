//package victor.training.spring.first;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//import victor.training.spring.altu.X;
//
//@Configuration // pt ca contine @Bean
//@RequiredArgsConstructor
//public class UnConfig {
//  @Bean
//  public X x1(Y y) { // numele 'x1'
//    return new X(y);
//  }
//  @Bean
//  public X xBis(Y y) { // 'xBis'
//    return new X(y);
//  }
//
////  @Bean
////  @ConfigurationProperties(prefix = "props")
////  public Props props() {
////    return new Props();
////  }
////  @Bean
////  @ConfigurationProperties(prefix = "props2")
////  public Props props2() {
////    return new Props();
////  }
//
//}
