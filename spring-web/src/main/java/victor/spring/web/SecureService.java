package victor.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

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
        boolean isAdmin = isAdmin();
        if (isAdmin) {
            System.out.println("Trimit SMS");
        }
    }

    private boolean isAdmin() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }

    @Autowired
    private TradeRepo tradeRepo;

    // TODO set criteria.includeConfidential = false if != ADMIN
    public List<TradeSearchResult> searchTrades(TradeSearchCriteria criteria) {
        criteria.canSeeConfidential = isAdmin();
        return tradeRepo.search(criteria);
    }

    // TODO allow method call only if user.jurisdictions.contains(countryCode)

}

class TradeSearchResult {
    private Long id;
}
class TradeSearchCriteria{
    public Integer minValue;
    public Integer maxValue;
    public Boolean canSeeConfidential;
}