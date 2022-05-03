package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
class AltaClasa {

	public void method() {

	}

	@Async
	// cand schimbi threadul, pierzi:
	// 1) SecurityContextHolder  > ai solutii:
	// 2) Connection+@Transactional
	public CompletableFuture<String> getLoggedInUsername() {
		return CompletableFuture.completedFuture(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {
	private final AltaClasa altaClasa;

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
		CurrentUserDto dto = new CurrentUserDto();
		// DONETODO: obtin user curent dintr-o metoda STATICA (😱) : sunt luate de pe Threadul curent : TODO read ce e aia ThreadLocal

		// TODO:
		//    authentification = userul chiar E john (identificarea) - treaba lui "singin" jar/app
		//  	authorization = ce ai voie sa faci (drepturi) stiind ca esti John, cu rolurile....
		// TODO autorizare in 5 moduri
		// TODO mange-ui permisiuni de pe central.
		String username = altaClasa.getLoggedInUsername().get();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object opaqueSecurityPrincipal = authentication.getPrincipal();
		String fullName = extractFullNameFromIDToken(opaqueSecurityPrincipal);
		dto.username = fullName + "(" + username + ")";
		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
		dto.authorities = stripRolePrefix(authentication.getAuthorities());

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

	private String extractFullNameFromIDToken(Object opaqueSecurityPrincipal) {
		try {
			KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal =(KeycloakPrincipal<KeycloakSecurityContext>)  opaqueSecurityPrincipal;
			IDToken idToken = keycloakPrincipal.getKeycloakSecurityContext().getIdToken();
			return idToken.getGivenName() + " " + idToken.getFamilyName().toUpperCase();
		} catch (Exception e) {
			log.warn("Not using keycloak: " + e, e );
			return "";
		}
	}

	private List<String> stripRolePrefix(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
			.map(grantedAuthority -> grantedAuthority.getAuthority().substring("ROLE_".length()))
			.collect(toList());
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
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
