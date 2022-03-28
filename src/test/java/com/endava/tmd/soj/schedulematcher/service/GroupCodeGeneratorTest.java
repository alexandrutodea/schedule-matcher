package com.endava.tmd.soj.schedulematcher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for GroupCodeGenerator")
class GroupCodeGeneratorTest {

    @ParameterizedTest(name = "Code of proper length gets generated for required length {0}")
    @DisplayName("The returned code is of the given length")
    @MethodSource("com.endava.tmd.soj.schedulematcher.service.GroupCodeGeneratorTest#generateLengthTestData")
    void returnedCodeMeetsLength(int length) {
        assertThat(GroupCodeGenerator.getGroupCode(length).length())
                .isEqualTo(length);
    }

    @Test
    @DisplayName("The returned code is only formed of lowercase letters")
    void returnedCodeIsAllLowercase() {
        char[] chars = GroupCodeGenerator.getGroupCode(16).toCharArray();
        for (char aChar : chars) {
            assertThat(aChar >= 97 && aChar <= 122)
                    .isTrue();
        }
    }

    @Test
    @DisplayName("An exception gets thrown if user requests a list of length lower than two")
    void exceptionGetsThrownForLengthLowerThanTwo() {
        assertThatThrownBy(() -> GroupCodeGenerator.getGroupCode(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Group code length cannot be smaller than 2");
    }

    private static Stream<Integer> generateLengthTestData() {
        return Stream.of(3, 4, 5, 6, 7, 8, 9, 10);
    }

}