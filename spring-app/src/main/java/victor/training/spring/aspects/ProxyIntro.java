package victor.training.spring.aspects;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths real = new Maths();
        Maths proxy = new MyHack(real);
        SecondGrade secondGrade = new SecondGrade(proxy);

        secondGrade.mathClass();
    }
}
class MyHack extends Maths {
    private final Maths delegate;
    MyHack(Maths delegate) {
        this.delegate = delegate;
    }
    @Override
    public int sum(int a, int b) {
        System.out.println("Calling sum with " + a + " " + b);
        return delegate.sum(a, b);
    }

    @Override
    public int product(int a, int b) {
        System.out.println("Calling product with " + a + " " + b);
        return delegate.product(a, b);
    }
}
// CR: the father wants to audit all the method calls (method call+params) to Maths!!!! to check for errors.
// restriction: don't edit code below the following line

// ------------------------
class SecondGrade {
    private final Maths maths;
    public SecondGrade(Maths maths) {
        this.maths = maths;
    }
    public void mathClass() {
        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}

class Maths {
    public int sum(int a, int b) {
        return a + b;
    }

    public int product(int a, int b) {
        return a * b;
    }
}

