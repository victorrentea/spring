package victor.spring.web;

import org.springframework.stereotype.Service;

@Service
public class SecuredService {
    // TODO [opt] propagate identity through async calls

    // TODO allow sendSMS only for role ADMIN or CRASH

    // TODO allow sendSMS only for role ADMIN or SKIP

    // TODO set criteria.includeConfidential = false if != ADMIN

    // TODO allow method call only if user.jurisdictions.contains(countryCode)
}
