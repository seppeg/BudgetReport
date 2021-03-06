package com.cegeka.project;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlContainerTestExtension implements AfterAllCallback, BeforeAllCallback {

    private static PostgreSQLContainer postgreSQLContainer;

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        postgreSQLContainer.stop();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        //"Expose daemon on tcp://localhost:2375 without TLS" must be checked.
        System.setProperty("io.netty.noUnsafe", "true");
        System.setProperty("org.testcontainers.shaded.io.netty.noUnsafe", "true");
        postgreSQLContainer = new PostgreSQLContainer("budgetreport/postgres:v1");
        postgreSQLContainer.start();
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }

    public static PostgreSQLContainer getPostgreSQLContainer() {
        return postgreSQLContainer;
    }
}
