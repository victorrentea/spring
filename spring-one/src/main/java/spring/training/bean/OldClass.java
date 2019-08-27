package spring.training.bean;

import spring.training.ThreadUtils;

// CAN'T TOUCH THIS !! Ta-na-na-na
class OldClass {
    private static OldClass INSTANCE;
    private OldClass() {
        currentCountry = geolocateCurrentCountry();
    }

    private String geolocateCurrentCountry() {
        ThreadUtils.sleep(1000);
        return "Romania";
    }

    public static OldClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OldClass();
        }
        return INSTANCE;
    }

    private String currentCountry;

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void method() {
        System.out.println("Hello world!");
    }
}
