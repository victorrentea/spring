package victor.training.spring.web.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Data // not in prod
public class Training {
    public static final int LOCK_DURATION_SECONDS = 20;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    @ManyToOne
    private Teacher teacher;
    @Enumerated(EnumType.STRING)
    private ProgrammingLanguage programmingLanguage;


    public Training() {
    }

    public Training(String name, LocalDate startDate) {
        this.name = name;
        this.startDate = startDate;
    }


    // optimistic
    @Version
    private Long version;

    // pessimistic locking (prevent opening the edit screen for the same record at the same time)
    @Setter(AccessLevel.NONE)
    private String inEditByUser;
    @Setter(AccessLevel.NONE)
    private LocalDateTime inEditSince;

    public void startEdit(String user) {
        if (inEditByUser != null &&
                !user.equals(inEditByUser) &&
                inEditSince.plusSeconds(LOCK_DURATION_SECONDS).isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Under edit by user " + user + " since " + inEditSince);
        }
        inEditByUser = user;
        inEditSince = LocalDateTime.now();
        log.info("GOT EDIT LOCK");
    }

    public void finishEdit(String user) {
        // TODO make sure any user navigating away from the edit screen calls this method to release the lock timely
        if (!user.equals(inEditByUser)) {
            throw new IllegalStateException("Cannot save changes : lock has expired and was acquired by " + inEditByUser);
        }
        inEditByUser = null;
        inEditSince = null;
        log.info("RELEASED EDIT LOCK");
    }
}
