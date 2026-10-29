package victor.training.sequence;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Draws target/sequence/{TestClass}.{testMethod}.svg from what the test method ran:
 * calls between beans, transaction frames, SQL. Put it on a Spring test class or method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ExtendWith(SequenceDiagramExtension.class)
public @interface SequenceDiagram {
}
