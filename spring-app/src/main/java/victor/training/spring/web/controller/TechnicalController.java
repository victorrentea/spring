package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.varie.ThreadUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {

	// ce e aia :
	// @Gill: sunt holurile normale de apeluri de functii decente, la vedere, cu param
	// si mai exista si aerisirea (canalu secret) prin care poti pasa date dintr-o functie in alta
	//   cata vreme ramai pe acelasi thread.
	// de ce ne doare? unde sunt folsoite astea ?
	//    1 SecurityContextHolder.getContext()
	//    2 JDBC Connection tine starea connex pe threadlocal ==> nu pot scrie pe aceeasi DB conn din 2 threaduri
	//    3 @Transactional - rranzactia curenta
	//    4 Logback MDC   %X google
	//    5 @Scope("request") pe care pui storeId / tenantId
	 private static final ThreadLocal<String> dateleMele = new ThreadLocal<>();

	public void method() {
//		ThreadUtils.sleepq(3000);
		log.info("Oare chiar " + dateleMele.get());

	}

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
//		JWTUtils.printTheTokens();
		dateleMele.set("sunt bou " + LocalDateTime.now());
		log.info("eu chiar " + dateleMele);
		method();

		CurrentUserDto dto = new CurrentUserDto();
		dto.username =
				CompletableFuture.supplyAsync(
						() -> SecurityContextHolder.getContext().getAuthentication().getName())
				.get(); // like a bull


		// A) role-based security
//		dto.role = extractOneRole(authentication.getAuthorities());
		// B) authority-based security
//		dto.authorities = authentication.getAuthorities().stream()
//				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		//<editor-fold desc="KeyCloak">
		//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		dto.username = authentication.getName();
//		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
//		dto.authorities = stripRolePrefix(authentication.getAuthorities());
//    // Optional:
//		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
//		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
//		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
		//</editor-fold>
		return dto;
	}

//	public static String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
//		// For Spring Security (eg. hasRole) a role is an authority starting with "ROLE_"
//		List<String> roles = authorities.stream()
//				.map(GrantedAuthority::getAuthority)
//				.filter(authority -> authority.startsWith("ROLE_"))
//				.map(authority -> authority.substring("ROLE_".length()))
//				.collect(Collectors.toList());
//		if (roles.size() == 2) {
//			log.debug("Even though Spring allows a user to have multiple roles, we wont :)");
//			return "N/A";
//		}
//		if (roles.size() == 0) {
//			return null;
//		}
//		return roles.get(0);
//	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsyncCalls() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Autowired
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome")
	public WelcomeInfo showWelcomeInfo(){
		return welcomeInfo;
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}

}

