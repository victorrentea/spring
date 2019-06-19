package spring.training.lifecycle;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

public class CumCreezProxyuriDeMana {
    public static void main(String[] args) {

        //asta ar face springul
        LabelService realImplem = new LabelService();
        realImplem.setCountryRepo(new CountryRepo());
        realImplem.load(Locale.ENGLISH);

        MethodInterceptor callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("Interceptez methods " + method.getName() + " chemata cu parametrii " + Arrays.toString(objects));
//                if (am in cache)
                return method.invoke(realImplem, objects);
            }
        };
        LabelService dinNimic = (LabelService) Enhancer.create(LabelService.class, callback);
        System.out.println("Ce e asta? " + dinNimic.getClass());
        System.out.println(dinNimic.getCountryName("RO"));
        System.out.println(dinNimic.getCountryName("ES"));
    }
}
