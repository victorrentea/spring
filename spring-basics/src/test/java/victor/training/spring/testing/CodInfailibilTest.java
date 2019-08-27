package victor.training.spring.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodInfailibilTest {
    @Autowired
    private Cuib cuib;

    @MockBean
    private DoamneDoamne mockDoamne;

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenNegativeAnswerToPray() {
        when(mockDoamne.pray(anyString())).thenReturn(-1);
        cuib.m();
    }
    @Test
    public void okWhenPositiveAnswerToPray() {
        when(mockDoamne.pray(anyString())).thenReturn(+1);
        cuib.m();
    }
}
