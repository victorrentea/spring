package victor.training.spring.web.controller.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

//@Slf4j
//@Component
//public class HttpRequestFilterPrintingHeaders implements Filter {
//}


// TODO victorrentea 2022-09-29: 1) print req headers
// TODO victorrentea 2022-09-29: 2) add respo header
// TODO victorrentea 2022-09-29: 3) every 10 calls, save the call details in DB.
