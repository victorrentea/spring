package victor.training.spring.first;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.Test;

public class ArchUnitTest {
   @Test
   public void check() {
      JavaClasses classes = new ClassFileImporter().importPackages("victor.training.spring.events");

      SliceRule sliceRule = SlicesRuleDefinition.slices()
          .matching("..events.(**)")
          .should().notDependOnEachOther();

      sliceRule.check(classes);


   }
}
