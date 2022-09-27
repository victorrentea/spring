package victor.training.spring.transaction.playground;

import org.springframework.context.ApplicationEvent;

public class SendEmailsAfterCommitEvent {
    private final String email;

    public SendEmailsAfterCommitEvent(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
