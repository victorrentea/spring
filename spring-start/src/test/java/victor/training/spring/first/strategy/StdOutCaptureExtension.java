package victor.training.spring.first.strategy;

import org.junit.jupiter.api.extension.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class StdOutCaptureExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

  private PrintStream originalOut;
  private ByteArrayOutputStream baos;

  @Override
  public void beforeEach(ExtensionContext context) {
    originalOut = System.out;
    baos = new ByteArrayOutputStream();
    // Tee: write to both the capture buffer and the original console
    OutputStream tee = new OutputStream() {
      @Override
      public void write(int b) {
        baos.write(b);
        originalOut.write(b);
      }

      @Override
      public void write(byte[] b, int off, int len) {
        baos.write(b, off, len);
        originalOut.write(b, off, len);
      }
    };
    System.setOut(new PrintStream(tee, true));
  }

  @Override
  public void afterEach(ExtensionContext context) {
    System.setOut(originalOut);
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
    return parameterContext.getParameter().getType() == StdOutCaptureExtension.class;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
    return this;
  }

  public String getCaptured() {
    return baos.toString();
  }
}
