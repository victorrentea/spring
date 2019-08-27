package spring.training.bean;

import javax.annotation.PostConstruct;

class SemiFinal {
    private final NewCreepyClass newCreepyClass;

    public SemiFinal(NewCreepyClass newCreepyClass) {
        this.newCreepyClass = newCreepyClass;
    }

    @PostConstruct
    public void hello() {
        System.out.println("Semifinal");
    }
}
