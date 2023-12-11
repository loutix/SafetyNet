package com.ocrooms.safetynet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetApplication {

    private final static Logger logger = LoggerFactory.getLogger(SafetyNetApplication.class);

    public static void main(String[] args) {
        logger.info("voici un message info");
        logger.warn("voici un message warn");
        logger.error("voici un message error");


        SpringApplication.run(SafetyNetApplication.class, args);

    }


}
