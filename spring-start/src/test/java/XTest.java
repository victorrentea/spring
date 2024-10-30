//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import victor.training.spring.first.Y;
//import victor.training.spring.lib.X;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//class XTest {
//
//    @Mock
//    private Y y;
//
//    private X x;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        x = new X();
//        x.y = y; // Manually inject the mock
//    }
//
//    @Test
//    void logic_whenYLogicReturnsZero() {
//        when(y.logic()).thenReturn(0);
//        int result = x.logic();
//        assertEquals(1, result);
//    }
//
//    @Test
//    void logic_whenYLogicReturnsPositiveNumber() {
//        when(y.logic()).thenReturn(5);
//        int result = x.logic();
//        assertEquals(6, result);
//    }
//
//    @Test
//    void logic_whenYLogicReturnsNegativeNumber() {
//        when(y.logic()).thenReturn(-5);
//        int result = x.logic();
//        assertEquals(-4, result);
//    }
//
//    @Test
//    void logic_whenYLogicReturnsMaxInteger() {
//        when(y.logic()).thenReturn(Integer.MAX_VALUE);
//        int result = x.logic();
//        assertEquals(Integer.MIN_VALUE, result);
//    }
//
//    @Test
//    void logic_whenYLogicReturnsMinInteger() {
//        when(y.logic()).thenReturn(Integer.MIN_VALUE);
//        int result = x.logic();
//        assertEquals(Integer.MIN_VALUE + 1, result);
//    }
//}