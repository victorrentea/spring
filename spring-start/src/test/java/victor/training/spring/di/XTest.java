package victor.training.spring.di;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XTest {

    @Mock
    Y y;
    @InjectMocks
    X x ;

    @Test
    void prod() {
//        X x = new X();
        when(y.prod()).thenReturn(2);

        int r = x.prod();
        assertThat(r).isEqualTo(3);
    }
}