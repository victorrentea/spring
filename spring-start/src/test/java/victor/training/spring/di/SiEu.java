package victor.training.spring.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootTest
public class SiEu {

    @Autowired
    private Environment environment;
    @BeforeEach
    final void before() {
        Map<String, Object> map = new HashMap();
        for(Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource propertySource = (PropertySource) it.next();
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
        System.out.println(map.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining("\n")));
    }
    @Test
    void explore() {

    }
}
