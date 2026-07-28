package victor.training.spring.transaction.playground;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

// Înregistrat în META-INF/services/com.p6spy.engine.event.JdbcEventListener (ServiceLoader).
// P6Spy nu are o categorie de log pentru BEGIN: în JDBC tranzacția "începe" la setAutoCommit(false),
// apelat de Spring/Hibernate pe conexiunea luată din pool — abia atunci, LAZY, la primul SQL!
public class P6SpyTxLifecycleLogger extends JdbcEventListener {
  private static final Logger log = LoggerFactory.getLogger("p6spy");

  @Override
  public void onAfterSetAutoCommit(ConnectionInformation ci, boolean newAutoCommit, boolean oldAutoCommit, SQLException e) {
    if (!newAutoCommit && oldAutoCommit) {
      log.info("0 ms|tx|connection {}|🟢 BEGIN (setAutoCommit(false))", ci.getConnectionId());
    } else if (newAutoCommit && !oldAutoCommit) {
      // Hikari face reset la autoCommit=true când conexiunea se întoarce în pool
      log.info("0 ms|tx|connection {}|⚪ autoCommit=true (conexiunea se întoarce în pool)", ci.getConnectionId());
    }
  }

  @Override // doar deschiderea FIZICĂ (umplerea pool-ului), nu împrumutul din pool:
  // Hikari stă DEASUPRA driverului p6spy, deci borrow-ul nu trece pe aici
  public void onAfterGetConnection(ConnectionInformation ci, SQLException e) {
    log.info("{} ms|connection|connection {}|🔌 OPEN physical connection", ci.getTimeToGetConnectionNs() / 1_000_000, ci.getConnectionId());
  }

  @Override
  public void onAfterConnectionClose(ConnectionInformation ci, SQLException e) {
    log.info("0 ms|connection|connection {}|🔌 CLOSE physical connection", ci.getConnectionId());
  }
}
