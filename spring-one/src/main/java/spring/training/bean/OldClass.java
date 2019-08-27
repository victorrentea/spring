package spring.training.bean;

import spring.training.ThreadUtils;

// no permission not change
class OldClass {
    private static OldClass INSTANCE;
    private OldClass() {
        currentIp = resolveIp();
    }

    private String resolveIp() {
        ThreadUtils.sleep(1000);
        return "127.0.0.1";
    }

    public static OldClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OldClass();
        }
        return INSTANCE;
    }

    private String currentIp;

    public String getCurrentIp() {
        return currentIp;
    }

    public void method() {
        System.out.println("Hello world!");
    }
}
