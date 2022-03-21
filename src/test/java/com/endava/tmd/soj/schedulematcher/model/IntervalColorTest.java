package com.endava.tmd.soj.schedulematcher.model;

import org.junit.jupiter.api.DisplayName;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for the Color enum")
class IntervalColorTest {

    private static Stream<Arguments> getTestData() {
        return Stream.of(
                Arguments.of(IntervalColor.RED, "#FF0000"),
                Arguments.of(IntervalColor.YELLOW, "#00F00"),
                Arguments.of(IntervalColor.YELLOW, "#00F00"),
                Arguments.of(IntervalColor.WHITE, "#FFFFFF")
        );
    }

    @ParameterizedTest(name = "Hex code for {0} is {1}")
    @MethodSource(value = "getTestData")
    void testSumMethod(IntervalColor color, String code) {
        assertThat(color
                .getCode())
                .isEqualTo(code);
    }

}