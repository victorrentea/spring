package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LazyLoadExceptions {
    private final TeacherRepo teacherRepo;

//    @Transactional // fix1 pros:
    @Scheduled(initialDelay = 1000, fixedDelay = 10000000)
    public void method() {
        creepy();
    }

    // daca chemi creepy din REST Controller -> merge lazy
    // daca o chemi din @Schedule, @MessageListener, @Async, (orice mod de a porni exec nu prin HTTP) -> o sa crape cu lazy load init ex.

    public void creepy() {
        System.out.println("ONCE!");
        List<Teacher> teachers = teacherRepo.findAll(); // NU asa cand vrei si copiiiiii pe usecaseul asta
        //        List<Teacher> teachers = teacherRepo.findAllWithChildren();
        System.out.println("AM adus teacheri fara sa incarc inca trainingurile lor");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getName() + " tine + " + teacher.getTrainings());
        }
        System.out.println("AFTER");
    }
}


@RestController
@RequiredArgsConstructor
class LazyLoadController {
    private final TeacherRepo teacherRepo;
    private final  LazyLoadExceptions lazyLoadExceptions;

    @GetMapping("api/lazy")
    public String lazy() {
        lazyLoadExceptions.creepy();
        return ":)";
    }

    @GetMapping("api/jr")
    public List<Teacher> jr() { // developerii e prosti
        return teacherRepo.findAll();
    }
}