package victor.training.spring.di;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class YTest {
    @InjectMocks
    Y y;
    @Mock
    Z z;

    @Test
    void prod() {
        y.prod();
    }
}