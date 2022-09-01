package victor.training.spring.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private EntityManager entityManager;

    @PostMapping("file")
//    @Transactional // will be necessary
    public void uploadFile(MultipartFile file) throws IOException {

        // 1 create a temp file and writing thge uploadde data in it
        // 2 save in DB from file.
        // 3 cleanuop file./

        File tempFile = Files.createTempFile("/tmp/myapp/" + file.getOriginalFilename(), ".dat").toFile();

        try (FileWriter writer = new FileWriter(tempFile)) {
            IOUtils.copy(file.getInputStream(), writer);
        }
        Session hibernateSession = (Session) entityManager.getDelegate();
        LobCreator lobCreator = Hibernate.getLobCreator(hibernateSession);

        InputStream fileInputStream = new FileInputStream(tempFile);
        Blob blob = lobCreator.createBlob(fileInputStream, tempFile.length());

        myFileEntityRepo.save(new MyFileEntity().setBlob(blob));

        if (!tempFile.delete()) {
            log.error("OMG!"); // will not work anymore.
        }
    }

    @Autowired
    private MyFileEntityRepo myFileEntityRepo;
}

interface MyFileEntityRepo extends JpaRepository<MyFileEntity, Long> {

}

@Entity
@Data
class MyFileEntity {
    @Id
    private Long id;
    private Blob blob;
}