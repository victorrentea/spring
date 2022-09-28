package victor.training.spring;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import victor.training.spring.web.entity.*;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Service
@RequiredArgsConstructor
public class DummyDataInsertedAtStartup {
    private final TrainingRepo trainingRepo;
    private final TeacherRepo teacherRepo;
    private final ProgrammingLanguageRepo languageRepo;
    private final UserRepo userRepo;
    private final ConnectionFactory connectionFactory;

    @PostConstruct
    public void run() throws Exception {

        log.info("Start init relational database");
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        List<String> sqlLines = asList(
                "DROP TABLE IF EXISTS users;",
//                "CREATE TABLE users (id serial primary key, name varchar);",
                "create table MESSAGE ( ID      BIGINT auto_increment primary key, MESSAGE CHARACTER VARYING(255));",

        "create table PROGRAMMING_LANGUAGE(ID   BIGINT auto_increment primary key, NAME CHARACTER VARYING(255));",

        "create table TEACHER(ID            BIGINT auto_increment primary key, CONTRACT_TYPE CHARACTER VARYING(255), NAME          CHARACTER VARYING(255));",


        "create table TRAINING(ID                      BIGINT auto_increment primary key, DESCRIPTION             CHARACTER VARYING(255), NAME                    CHARACTER VARYING(255),START_DATE              TIMESTAMP, PROGRAMMING_LANGUAGE_ID BIGINT, TEACHER_ID              BIGINT, constraint FK2Y1IOV710XNQ3HXCSEDG9KGOY foreign key (TEACHER_ID) references TEACHER, constraint FKD1NB07UIV3MM11CH4DL74A8MR foreign key (PROGRAMMING_LANGUAGE_ID) references PROGRAMMING_LANGUAGE);",

        "create table USERS(ID       BIGINT auto_increment primary key, NAME     CHARACTER VARYING(255), ROLE     CHARACTER VARYING(255), USERNAME CHARACTER VARYING(255));"






        );
        for (String sql : sqlLines) {
            log.info("Executing {} ...", sql);
            databaseClient
                    .sql(sql)
                    .fetch()
                    .rowsUpdated()
                    .block();
        }
        log.info("done");


        log.info("Inserting dummy data");

        ProgrammingLanguage java = languageRepo.save(new ProgrammingLanguage("Java")).block();
        ProgrammingLanguage php = languageRepo.save(new ProgrammingLanguage("PHP")).block();

        Teacher victor = teacherRepo.save(new Teacher("Victor").setContractType(ContractType.INDEPENDENT)).block();
        Teacher ionut = teacherRepo.save(new Teacher("Ionut").setContractType(ContractType.PART)).block();

        Training spring = new Training("Spring Framework", new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000L))
                .setDescription("All about Spring")
                .setProgrammingLanguageId(java.getId())
                .setTeacherId(victor.getId())
                ;
        Training jpa = new Training("JPA", new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000L))
                .setDescription("The coolest standard in Java EE")
                .setProgrammingLanguageId(java.getId())
                .setTeacherId(victor.getId())
                ;
        Training javaBasic = new Training("Java Basic", new Date(System.currentTimeMillis() + 20 * 24 * 60 * 60 * 1000L))
                .setDescription("The new way of doing Single Page Applications")
                .setProgrammingLanguageId(java.getId())
                .setTeacherId(ionut.getId())
                ;
        Training patterns = new Training("Design Patterns", new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000L))
                .setDescription("Design Thinking")
                .setProgrammingLanguageId(php.getId())
                .setTeacherId(victor.getId())
                ;

        trainingRepo.save(spring).block();
        trainingRepo.save(jpa).block();
        trainingRepo.save(javaBasic).block();
        trainingRepo.save(patterns).block();

        userRepo.save(new User("Boss", "admin", UserRole.ADMIN)).block(); // only manages Victor, not Ionut
        userRepo.save(new User("Clerk", "user", UserRole.USER)).block();

    }


}
