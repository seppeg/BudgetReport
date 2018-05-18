package com.cegeka.camis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CamisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamisApplication.class, args);
    }

}
