package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class TechnicalController {
	private final UserService userService;



	@PostMapping("upload")
	public void method(@RequestBody MultipartFile multipartFile) throws IOException {
//		multipartFile.getContentType() == "applicatio/octet-stream
//	"
//			multipartFile.getOriginalFilename().endsWith(".exe");


		String r = UUID.randomUUID().toString();
		String fileName = "in-" + r + ".tmp";

//		persistinDB(r, multipartFile.getOriginalFilename());


//		try (FileOutputStream output = new FileOutputStream(new File("/temp/in/", multipartFile.getOriginalFilename()))) { // aici hackeru trimite:
		try (FileOutputStream output = new FileOutputStream(new File("/temp/in/", fileName))) {
			// ../../

//		byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
//			IOUtils.write(bytes, output);
			IOUtils.copy(multipartFile.getInputStream(), output);

		}

	}



	@GetMapping("api/user/current")
	public String getCurrentUsername() throws ExecutionException, InterruptedException {
		// TODO implement me
//		return "TODO:user";
//		SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext().);

		// try getting the user from an Async task:
//			return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
		return userService.manualThreadPools();
	}

//	@GetMapping
//	void takeMeHome(HttpServletResponse response) throws IOException {
//		String sanitizedUrl = "whitelisted"; // NICIODATA direct URL-uri primite de la useri
//		response.sendRedirect(sanitizedUrl); // JSP/JSF
//	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	// TODO use authorities in FR
	@GetMapping("api/user/current/authorities")
	public List<String> getCurrentUserAuthorities() throws Exception {
		SecurityUser securityUserOnSession = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return securityUserOnSession.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

	//	@Autowired  // TODO Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome-info")
	public String showWelcomeInfo() {
		// TODO return welcomeInfo;
		return "Welcome! What's your temperature?";
	}
	// TODO [SEC] URL-pattern restriction: admin/**
//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}
	@GetMapping("admin/heapdumo")
	public String heapdump() {
		return "What does this red button do?     ... [Missile Launched]";
	}
}
