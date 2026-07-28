package victor.training.spring.transaction.propagation;

import jakarta.persistence.*;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    protected AuditLog() {}

    public AuditLog(String message) {
        this.message = message;
    }
}
