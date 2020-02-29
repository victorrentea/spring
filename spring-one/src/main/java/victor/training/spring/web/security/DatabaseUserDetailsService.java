//package victor.training.spring.web.security;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//import org.springframework.stereotype.Component;
//import victor.training.spring.web.domain.User;
//import victor.training.spring.web.repo.UserRepo;
//
//import java.util.Optional;
//
//@Component
//@Slf4j
//// TODO implement JWT with AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
//// Hint: JwtPrincipal jwtPrincipal = (JwtPrincipal) token.getPrincipal();
//public class DatabaseUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UserRepo userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.debug("Lookup username {} in database", username);
//        Optional<User> userOpt = userRepository.getForLogin(username);
//
//        if (!userOpt.isPresent()) {
//            throw new UsernameNotFoundException("User '" + username + "' not in database");
//        }
//        User user = userOpt.get();
//        log.debug("Successful login");
//        return new SecurityUser(user.getUsername(),
//                user.getProfile().permissions,
//                user.getManagedTeacherIds());
//    }
//}
//
