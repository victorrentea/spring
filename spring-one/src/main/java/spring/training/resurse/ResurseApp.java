package spring.training.resurse;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

//@PropertySource("/config.properties")
@SpringBootApplication
public class ResurseApp implements CommandLineRunner{
	private final static Logger log = LoggerFactory.getLogger(ResurseApp.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ResurseApp.class, args);
	}
	
@Autowired
private B b;
	
	public void run(String... args) throws Exception {
		 System.out.println("Hello!");
		 b.callWS();
//		 String url = "http://195.23.235.23:5345/ws";
		
	}
}
@Service
class B {
	@Autowired
	private A a;
	
	public void callWS() {
		a.callWS();
	}
}

@Service
class A {
	private final static Logger log = LoggerFactory.getLogger(A.class);
	
	@Value("${webservice.endpoint}")
	private String url;
	
	@Value("${webservice.timeout:5000}")
	private long timeout;
	
	@Autowired
	private ApplicationContext spring;
	
	@PostConstruct
	public void init() {
		String url = spring.getEnvironment().getProperty("webservice.endpointXX");
		System.out.println("Lucrez cu URL: " + url + " si timeout: " + timeout);
	}
	public void callWS() {
		 System.out.println("Apelez endpointul " + url);
	}
	
}



