package props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.File;

@SpringBootApplication
@PropertySource("file:///c:/workspace/config.properties")
public class PropsApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PropsApp.class, args);
    }

    @Value("${proprMea}")
    private String prop;
    @Value("${doishpe}")
    private int doishpe;
    @Value("${tempFolder.test.in}")
    private File tempFolder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(prop);
        System.out.println(doishpe);
        System.out.println(tempFolder.toURI());
    }
}
