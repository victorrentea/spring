//package victor.training.spring.web.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//import victor.training.spring.web.entity.UserRole;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class RolesFromTokenToLocalAuthorities implements GrantedAuthoritiesMapper {
//    @Override
//    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        List<UserRole> matchingRoles = authorities.stream()
//                .map(a -> UserRole.valueOfOpt(a.getAuthority()))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
//        log.debug("Found roles in token: {}", matchingRoles);
//        return matchingRoles.stream()
//                .flatMap(userRole -> userRole.getAuthorities().stream())
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//}