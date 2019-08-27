package victor.training.spring.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodInfailibilTest {
    @Autowired
    private Cuib cuib;

    @Mock
    private DoamneDoamne doamne;


    @Test(expected = IllegalArgumentException.class)
    public void throwWhenNegativeAnswerToPray() {
//        when(doamne.pray(anyString())).thenReturn(-1);
        Alalalt.rez = -1;
        cuib.m();
    }
    @Test
    public void okWhenPositiveAnswerToPray() {
//        when(doamne.pray(anyString())).thenReturn(+1);
        Alalalt.rez = +1;
        cuib.m();
    }
}

@Service
@Primary
class Alalalt extends DoamneDoamne {
    public static int rez = 0;
    @Override
    public int pray(String prayer) {
        return rez;
    }
}