package victor.training.spring.web.security;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
//@Component
public class DatabaseUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Lookup username {} in database", username);
        User user = userRepository.getForLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not in database"));

        log.debug("Successful login : " + user.getProfile().authorities);
        // WARNING: the implem of SecurtyUser class is hacked to expect the password of the user == its username
        return new SecurityUser(user.getUsername(),
                user.getProfile().authorities,
                user.getManagedTeacherIds());
    }


    public void method() {

        method2();
    }

    @SneakyThrows
    public void method2() {
//        FileInputStream fileInputStream = new FileInputStream("a.txt");
        throw new IOException();
    }


}

