package helloWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages="helloWorld")
public class HelloWorldApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HelloWorldApp.class, args);
        System.out.println(Arrays.asList(context.getBeanDefinitionNames()));
    }
}
