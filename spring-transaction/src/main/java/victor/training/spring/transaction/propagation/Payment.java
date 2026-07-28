package victor.training.spring.transaction.propagation;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String reference;

    protected Payment() {}

    public Payment(String reference) {
        this.reference = reference;
    }
}
