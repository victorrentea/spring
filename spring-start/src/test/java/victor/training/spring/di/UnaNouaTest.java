package victor.training.spring.di;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnaNouaTest {
    @Mock Alta alta;
    @Mock Alta2 alta2;
    @InjectMocks UnaNoua unaNoua;

    @Test
    void run() throws Exception {
//        UnaNoua unaNoua = new UnaNoua(new Alta(), new Alta2());
//        UnaNoua unaNoua = new UnaNoua(       );
//        unaNoua.alta = new Alta();\
        unaNoua.run("a");
    }
}