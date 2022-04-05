package victor.training.spring.bean;

// CAN'T TOUCH THIS !! Ta-na-na-na
class OldClass {
    private static OldClass INSTANCE;
    private OldClass() {
        currentCountry = geolocateCurrentCountry();
    }

    private String geolocateCurrentCountry() {
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
