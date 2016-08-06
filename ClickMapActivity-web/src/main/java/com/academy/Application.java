package com.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Daniel Palonek on 2016-07-29.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.academy")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder app){
        return app.sources(Application.class);
    }

    public static void main(String [] args) {
        SpringApplication.run(Application.class,args);
    }
}
