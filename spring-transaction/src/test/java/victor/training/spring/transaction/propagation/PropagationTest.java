package victor.training.spring.transaction.propagation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.spring.transaction.propagation.PropagationApp;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = PropagationApp.class, properties = {
    "spring.datasource.url=jdbc:h2:mem:propagation-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class PropagationTest {

    @Autowired PaymentRepo paymentRepo;
    @Autowired AuditLogRepo auditLogRepo;
    @Autowired PaymentService paymentService;
    @Autowired PaymentImporter paymentImporter;
    @Autowired ReportService reportService;
    @Autowired TransactionTemplate transactionTemplate;

    @BeforeEach
    void cleanup() {
        paymentRepo.deleteAll();
        auditLogRepo.deleteAll();
    }

    // ─── Exemplul 1 ──────────────────────────────────────────────────────────────
    // Dacă processPayment aruncă excepție → Payment se rollback-ează.
    // Dar AuditLog (REQUIRES_NEW) s-a committtat deja în propria lui tranzacție.
    @Test
    void auditLogSupraviețuieșteRollback() {
        assertThatThrownBy(() -> paymentService.processPayment("REF-001"))
                .hasMessage("Gateway timeout");

        assertThat(paymentRepo.count()).isEqualTo(0);   // rolled back
        assertThat(auditLogRepo.count()).isEqualTo(1);  // supraviețuit!
    }

    // ─── Exemplul 2 ──────────────────────────────────────────────────────────────
    // "REF-DUP" apare de două ori → prima inserție reușește, a doua aruncă excepție (unique).
    // Importerul o prinde și continuă. Celelalte referințe sunt salvate normal.
    @Test
    void importBatch_săritePesteDuplicate() {
        paymentImporter.importAll(List.of("REF-A", "REF-DUP", "REF-B", "REF-DUP"));
        //                                              ↑ prima salvată OK       ↑ a doua aruncă unique violation → skip

        assertThat(paymentRepo.count()).isEqualTo(3); // REF-A, REF-DUP (prima), REF-B
    }

    // ─── Exemplul 3 ──────────────────────────────────────────────────────────────
    // Chiar dacă suntem apelați din interiorul unei tranzacții active,
    // NOT_SUPPORTED o suspendă → metoda rulează fără tranzacție.
    @Test
    void notSupported_suspendăTranzacțiaActivă() {
        boolean txActivăÎnăuntru = transactionTemplate.execute(
                status -> reportService.isTransactionActive() // NOT_SUPPORTED suspendă tranzacția din transactionTemplate
        );

        assertThat(txActivăÎnăuntru).isFalse(); // deși eram într-o tranzacție, ReportService a rulat fără ea
    }
}
