package restaurant.rating;

import org.springframework.context.support.GenericXmlApplicationContext;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: ");
            Arrays.stream(appCtx.getBeanDefinitionNames())
                    .forEach(System.out::println);
        }
    }
}