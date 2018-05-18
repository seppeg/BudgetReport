package com.cegeka.project;

import com.cegeka.project.service.ProjectStreams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableBinding(ProjectStreams.class)
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(@Value("${frontend.origin}") String frontEndOrigin) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/project")
                        .allowedOrigins(frontEndOrigin)
                        .allowedMethods("GET", "PUT");
            }
        };
    }
}
