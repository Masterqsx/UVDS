package helloWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="helloWorld")
public class HelloWorldApp {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApp.class, args);
    }
}
