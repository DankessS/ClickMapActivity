import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Created by DankessS on 2016-07-29.
 */
public class Application {

    public static void main(String [] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class,args);

        System.out.println("Beans provided by Spring Boot:");
        String [] beans = ctx.getBeanDefinitionNames();
        Arrays.sort(beans);
        for(String bean: beans) {
            System.out.println(bean);
        }
    }
}
