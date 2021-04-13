package victor.training.spring.events;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.Test;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static java.util.stream.Collectors.joining;

public class ObserverArchUnitTest {

   @Test
   public void independentSubdomains() {
      JavaClasses classes = new ClassFileImporter()
          .importPackages("victor.training.spring.events");

//      String names = classes.stream().map(JavaClass::getSimpleName).collect(joining());
//      System.out.println("Studying classes: " + names);

      SliceRule sliceRule = SlicesRuleDefinition.slices()
          .matching("..events.(**)")
          .should().notDependOnEachOther()
          .ignoreDependency(alwaysTrue(), resideInAPackage("..events"))
          ; // allow dependencies to .events

      // progressive strangling the monolith
      EvaluationResult evaluationResult = sliceRule.evaluate(classes);

      int violations = evaluationResult.getFailureReport().getDetails().size();
      System.out.println("Got Violations: " + violations);

      sliceRule.check(classes);


   }
}
