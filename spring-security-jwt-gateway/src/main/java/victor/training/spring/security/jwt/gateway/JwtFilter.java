package victor.training.spring.security.jwt.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

@Slf4j
@Component
public class JwtFilter extends ZuulFilter {

	@Value("${jwt.secret}")
	private String jwtSecret;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;

    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getUserPrincipal() == null) {
            log.warn("Request without user principal, Jwt filter will not be applied");
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        Principal principal = request.getUserPrincipal();
        Objects.nonNull(principal);


        String jwtToken = Jwts.builder()
                .setSubject(principal.getName())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        log.info("Add jwt token: {}", jwtSecret);

        ctx.addZuulRequestHeader("Authorization", "Bearer " + jwtToken);
        return null;
    }
}
