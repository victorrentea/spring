package victor.training.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.varie.ThreadUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class FiltruLuiAlexB implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RestTemplate rest = new RestTemplate();

//        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> bigJohn.longProcessCeDureaza2min_siNUVreauSaIntarziiRaspunsurile());
//        cf.exc

        bigJohn.longProcessCeDureaza2min_siNUVreauSaIntarziiRaspunsurile();
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Marire", "nu");
        chain.doFilter(request, response);
    }

    @Autowired
    private BigJohn bigJohn;
}
@Component
class BigJohn {
    @Async
    public void longProcessCeDureaza2min_siNUVreauSaIntarziiRaspunsurile() {
        System.out.println("Rulez");
        ThreadUtils.sleepq(4000);
        throw new IllegalArgumentException("Value!");
    }
}
