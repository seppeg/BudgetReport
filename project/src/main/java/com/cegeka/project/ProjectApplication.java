package com.cegeka.project;

import com.cegeka.project.infrastructure.ProjectStreams;
import com.cegeka.project.infrastructure.debounce.DebounceAspect;
import com.cegeka.project.infrastructure.debounce.Debouncer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@SpringBootApplication
@EnableBinding(ProjectStreams.class)
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public DebounceAspect debounceAspect(Debouncer debouncer){
        return new DebounceAspect(debouncer);
    }
}
