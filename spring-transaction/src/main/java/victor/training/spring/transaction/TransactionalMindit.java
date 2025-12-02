package victor.training.spring.transaction;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Transactional(rollbackFor = Exception.class) // ~ @TransactionAttribute (EJB)
@Retention(RUNTIME) // survives javac
public @interface TransactionalMindit {
}
