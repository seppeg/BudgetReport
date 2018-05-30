package com.cegeka.camis.booking;

import com.cegeka.camis.connection.StubWorkOrderConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static com.cegeka.camis.booking.CamisBooking.Builder.booking;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CamisRepositoryTest {

    private static EmbeddedDatabase db;

    @BeforeAll
    public static void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .addDefaultScripts()
                .build();
    }

    @Test
    public void testDataAccess() {
        List<CamisBooking> actual = new CamisRepository(db, new StubWorkOrderConfig("COCFL871.004","COCFL874.004","COCFL860.004")).findActualsFor(201801);

        Assertions.assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .containsOnly(booking()
                        .workorder("COCFL871.004")
                        .date(new Date(2018-1900,9, 10))
                        .description("Comments are cool")
                        .hours(new BigDecimal(8.00))
                        .employee("9988946")
                        .build(),
                        booking()
                                .workorder("COCFL874.004")
                                .date(new Date(2018-1900,9, 9))
                                .description("Comments are cool 2 ")
                                .hours(new BigDecimal(6.00))
                                .employee("9988947")
                                .build(),
                        booking()
                                .workorder("COCFL860.004")
                                .date(new Date(2018-1900,8, 10))
                                .description("Comments are not cool")
                                .hours(new BigDecimal(2.25))
                                .employee("9918946")
                                .build());
    }

    @AfterAll
    public static void tearDown() {
        db.shutdown();
    }



}