package victor.training.spring.varie;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(
        objectName = "bean:name=testBean4",
        description = "My Managed Bean",
        log = true,
        logFile = "jmx.log",
        currencyTimeLimit = 15,
        persistPolicy = "OnUpdate",
        persistPeriod = 200,
        persistLocation = "foo",
        persistName = "bar")
public class SampleMBean {
    @ManagedOperation
    public void dummy() {
        System.out.println("Called over JMX");
    }
}
