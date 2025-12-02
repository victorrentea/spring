//package victor.training.spring.transaction;
//
//import com.tngtech.archunit.core.domain.JavaClass;
//import com.tngtech.archunit.core.domain.JavaMethod;
//import com.tngtech.archunit.lang.*;
//import com.tngtech.archunit.junit.AnalyzeClasses;
//import com.tngtech.archunit.junit.ArchTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
//
//@AnalyzeClasses(packages = "victor.training.spring") // adaptează pachetul
//public class TransactionalShouldNotThrowCheckedTest {
//
//  @ArchTest
//  static final ArchRule transactional_methods_must_not_declare_checked_exceptions =
//      methods()
//          .that().areAnnotatedWith(Transactional.class)
//          .or().areDeclaredInClassesThat().areAnnotatedWith(Transactional.class)
//          .should(notDeclareCheckedExceptions());
//
//  private static ArchCondition<JavaMethod> notDeclareCheckedExceptions() {
//    return new ArchCondition<>("not declare checked exceptions") {
//      @Override
//      public void check(JavaMethod method, ConditionEvents events) {
//        for (JavaClass thrownType : method.getThrowsClause()) {
//          boolean isChecked =
//              thrownType.isAssignableTo(Exception.class)
//                  && !thrownType.isAssignableTo(RuntimeException.class);
//
//          if (isChecked) {
//            String message = String.format(
//                "Method %s declares checked exception %s",
//                method.getFullName(), thrownType.getFullName()
//            );
//            events.add(SimpleConditionEvent.violated(method, message));
//          }
//        }
//      }
//    };
//  }
//}