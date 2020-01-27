package hello;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

@SpringBootApplication
public class GatewayApplication extends WebSecurityConfigurerAdapter {


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        configure2SSL(restTemplate);
        return restTemplate;
    }

//    private void configure2SSL(RestTemplate restTemplate) {
//        try {
//            KeyStore keyStore = KeyStore.getInstance("jks");
//            try (InputStream inputStream = new ClassPathResource("gateway.jks").getInputStream()) {
//                keyStore.load(inputStream, "parola".toCharArray());
//            }
//
//            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
//                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//                    .loadKeyMaterial(keyStore, "parola".toCharArray()) // present certificate to server
//                    .build(),
//                    NoopHostnameVerifier.INSTANCE);
//
//            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
//                    .setMaxConnTotal(5)
//                    .setMaxConnPerRoute(5)
//                    .build();
//
//            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//            requestFactory.setReadTimeout(10_000);
//            requestFactory.setConnectTimeout(10_000);
//            restTemplate.setRequestFactory(requestFactory);
//        } catch (Exception e) {
//            throw new RuntimeException("Could not create RestTemplate", e);
//        }
//    }


    public static void main(String[] args) throws Throwable {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll();

    }

}
