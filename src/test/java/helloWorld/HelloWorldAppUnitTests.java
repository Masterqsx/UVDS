package helloWorld;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;

public class HelloWorldAppUnitTests extends JerseyTest {

    @Override
    protected Application configure() {
        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldApp.class);
        return new JerseyConfig().property("contextConfig", context);
    }

    @Test
    public void helloWorldUnitTest() {
        String res = target("v0.0.0").request().get(String.class);
        assertEquals(res, "Hello World");
    }
}
