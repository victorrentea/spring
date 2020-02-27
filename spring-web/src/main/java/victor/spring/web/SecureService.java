package victor.spring.web;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class SecureService {
    // TODO [opt] propagate identity through async calls

    // TODO allow sendSMS only for role ADMIN or CRASH

    // Undeva adanc, la 7 nivele de call sub Controller
    @PreAuthorize("hasRole('ADMIN')")
    public void sendSMS() {
        System.out.println("Trimit SMS");
    }

    // TODO allow sendSMS only for role ADMIN or SKIP
//    @Async // TODO
    public void sendSMSSafe() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        if (isAdmin) {
            System.out.println("Trimit SMS");
        }
    }

    // TODO set criteria.includeConfidential = false if != ADMIN

    // TODO allow method call only if user.jurisdictions.contains(countryCode)
}
