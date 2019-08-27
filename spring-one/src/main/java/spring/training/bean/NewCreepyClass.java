package spring.training.bean;

import javax.annotation.PostConstruct;
import java.util.Arrays;

class NewCreepyClass {
    private final OldClass old;
    private final String[] ipParts;

    NewCreepyClass(OldClass old, String[] ipParts) {
        this.old = old;
        this.ipParts = ipParts;
    }

    @PostConstruct
    public void hello() {
        System.out.println("Post Construct in NewCreepyClass : " + Arrays.toString(ipParts));
        old.method();
    }
}
