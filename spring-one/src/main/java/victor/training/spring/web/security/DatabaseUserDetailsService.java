package victor.training.spring.web.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

@Slf4j
public class DatabaseUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Lookup username {} in database", username);
        User user = userRepository.getForLogin(username)
            // unele app daca nu stiu de user, il insera automat aici
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not in database"));
        // Dupa login insa, 99% app au userul logat in baza lor de date

        log.info("Successful login");
        // WARNING: the implem of SecurtyUser class is hacked to expect the password of the user == its username
        return new SecurityUser(user.getUsername(),
                user.getProfile().permissions,
                user.getManagedTeacherIds());
    }


}

