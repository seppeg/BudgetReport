package com.cegeka.camis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Fail {

    @Test
    void failTest() {
        assertThat(true).isFalse();
    }
}
