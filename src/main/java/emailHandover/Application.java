package emailHandover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Application class runs the whole emailHandover server */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        ReadProperties.readConfig();
        SpringApplication.run(Application.class);


    }


}
