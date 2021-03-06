package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import victor.training.spring.web.domain.Teacher;

import java.util.Date;

@Component
public class EmailSender {

    // TODO [SEC] only ADMIN is allowed to send emails. other roles: (a) crash or (b) silently skip
    public void sendScheduleChangedEmail(Teacher teacher, String trainingName, Date newDate) {
        System.out.println("SENDING EMAIL TO TEACHER " + teacher.getName() + " for training " + trainingName + " moved to date " + newDate);

    }
}
