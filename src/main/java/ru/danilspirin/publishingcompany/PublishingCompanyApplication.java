package ru.danilspirin.publishingcompany;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
@Slf4j
public class PublishingCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishingCompanyApplication.class, args);
    }
}
