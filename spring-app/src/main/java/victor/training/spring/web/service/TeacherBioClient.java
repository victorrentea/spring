package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import victor.training.spring.ThreadUtils;

@Component
@Slf4j
public class TeacherBioClient {
    // TODO cacheable
    public String retrieveBiographyForTeacher(long teacherId) {
        log.debug("Calling external web endpoint... (takes time)");
        ThreadUtils.sleepq(500);
        String result = "Amazing bio for teacher " + teacherId;
        log.debug("Got result");
        return result;
    }
}
