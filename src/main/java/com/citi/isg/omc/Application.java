package com.citi.isg.omc;

import com.citi.isg.omc.handover.ReadProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Application class runs the whole emailHandover server */
@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws Exception {
        ReadProperties.readConfig();
        SpringApplication.run(Application.class);

        logger.debug("--Application Started--");
    }


}
