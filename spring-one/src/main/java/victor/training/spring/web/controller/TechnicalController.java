package victor.training.spring.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

@RestController
public class TechnicalController {
	@Autowired
	private UserService userService;

	@GetMapping("rest/user/current")
	public String getCurrentUsername() throws ExecutionException, InterruptedException {
//		return SecurityContextHolder.getContext().getAuthentication().getName();
		// try getting the user from an Async task:
		return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

//	@Autowired  // TODO Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome-info")
	public String showWelcomeInfo(){
		// TODO return welcomeInfo;
		return "Welcome! What's your temperature?";
	}

	@GetMapping("ping")
	public String ping() throws ExecutionException, InterruptedException {
		return "Pong " + getCurrentUsername();
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}


	@Scheduled(fixedRate = 10000)
	public void method() {
	    // ma uit in /tmp daca mai e macar 10G de spatiu. daca nu -> mail
	}

//	@PostMapping()
	public void uploadFile(HttpServletRequest request) throws IOException {

		File tmpFile = Files.createTempFile("data", ".dat").toFile(); //    eg /tmp/data-001.dat  - Doamne ajuta sa ai quota acolo destula

		try (OutputStream outputStream = new FileOutputStream(tmpFile)) {
			IOUtils.copy(request.getInputStream(), outputStream);


			// si acum am un fisier de 1G pe disc. Ce fac cu el ?

			// 1) uploadezi in DB
			// 2) parsezi ca chinezu batran si le inseri rand cu rand
			// 3) trimiti la altul sFTP
		}

		// MAI FITZOS: NIO: Java NIO iti permite sa copiezi din bufferul placii de retea direct in HDD fara sa treci prin RAM?!
		// Direct Memory Access (DMA) controller.

//FileChannel
		boolean deleted = tmpFile.delete();
		if (!deleted) {
			// TODO mail SMS like pe FB
		}

	}
}
