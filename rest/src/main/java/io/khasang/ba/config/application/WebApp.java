package io.khasang.ba.config.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

//TODO dog_nail-1 => Do we need EntityManager?
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
