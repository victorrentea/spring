package victor.spring.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        return currentUser().getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }

    private User currentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    @Autowired
    private TradeRepo tradeRepo;

    // TODO set criteria.includeConfidential = false if != ADMIN
    public List<TradeSearchResult> searchTrades(TradeSearchCriteria criteria) {
        criteria.canSeeConfidential = isAdmin();
        return tradeRepo.search(criteria);
    }

    // TODO allow method call only if user.jurisdictions.contains(countryCode)

    @Autowired
    private UserJurisdictionChecker jurisdictionChecker;

    @PreAuthorize("hasRole('USER') && @userJurisdictionChecker.canAccessCountry(principal.username,#countryIso)")
    public void exportTrades(String countryIso) {
//        if (!jurisdictionChecker.canAccessCountry(currentUser().getUsername(), countryIso)) {
//            throw new IllegalArgumentException("N-ai voie!");
//        }
        System.out.println("Export trades " + countryIso);
    }
}

@Component
@RequiredArgsConstructor
class UserJurisdictionChecker {
    private final UserJurisdictionsRepo repo;
    public boolean canAccessCountry(String username, String countryIso) {
        return repo.getUserCountries(username).contains(countryIso);
    }
}

@Repository
class UserJurisdictionsRepo {
    public List<String> getUserCountries(String username) {
        if (username.equals("test")) {
            return Arrays.asList("RO", "ES");
        } else {
            return Arrays.asList("FR");

        }
    }
}

class TradeSearchResult {
    private Long id;
}
class TradeSearchCriteria{
    public Integer minValue;
    public Integer maxValue;
    public Boolean canSeeConfidential;
}