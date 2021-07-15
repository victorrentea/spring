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
        log.debug("Looking username {} in database", username);
        User user = userRepository.getForLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not in database"));

        log.debug("Successful logged in " + user);
        // WARNING: the implem of SecurityUser class is hacked to expect the password of the user == its username
        return new SecurityUser(user.getUsername(),
                user.getRole(),
                user.getManagedTeacherIds());
    }


}

