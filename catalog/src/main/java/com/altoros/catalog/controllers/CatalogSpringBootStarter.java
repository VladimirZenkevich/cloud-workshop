package com.altoros.catalog.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by uladzimir.ziankevich on 12/11/2015.
 */
@SpringBootApplication
public class CatalogSpringBootStarter extends SpringBootServletInitializer {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CatalogSpringBootStarter.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(CatalogSpringBootStarter.class, args);
    }

}
