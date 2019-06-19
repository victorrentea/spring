package spring.training.first;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnTestUnitar {

    @Mock
    private B b;

    @InjectMocks
    private A a;

    @Test
    public void testBun() {
        when(b.n()).thenReturn("a");
        assertEquals("A", a.m());
    }
}
