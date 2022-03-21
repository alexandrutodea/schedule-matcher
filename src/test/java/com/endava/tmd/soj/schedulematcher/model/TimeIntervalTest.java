package com.endava.tmd.soj.schedulematcher.model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Test for the Time Interval class")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class TimeIntervalTest {

    private final TimeInterval timeInterval = new TimeInterval(12, 14);

    @Test
    @DisplayName("An exception gets thrown for a negative starting hour")
    void startingHourIsNegative() {
        assertThatThrownBy(() -> new TimeInterval(-1, 11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The starting and ending hours cannot be negative");
    }

    @Test
    @DisplayName("An exception gets thrown for a negative ending hour")
    void endingHourIsNegative() {
        assertThatThrownBy(() -> new TimeInterval(11, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The starting and ending hours cannot be negative");
    }

    @Test
    @DisplayName("An exception gets thrown if the starting hour is equal to the ending hour")
    void startingHourIsEqualToEndingHour() {
        assertThatThrownBy(() -> new TimeInterval(11, 11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The starting hour has to be strictly smaller than the ending hour");
    }

    @Test
    @DisplayName("An exception gets thrown if the starting hour is greater than the ending hour")
    void startingHourIsGreaterThanTheEndingHour() {
        assertThatThrownBy(() -> new TimeInterval(12, 11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The starting hour has to be strictly smaller than the ending hour");
    }

    @Test
    @DisplayName("Ending time getter functions properly")
    void startingTimeGetterWorksProperly() {
        assertThat(timeInterval
                .getStart())
                .isEqualTo(12);
    }

    @Test
    @DisplayName("Starting time getter works properly")
    void endingTimeGetterWorksProperly() {
        assertThat(timeInterval
                .getEnd())
                .isEqualTo(14);
    }

    @Test
    @DisplayName("Color getter works properly")
    void colorGetterWorksProperly() {
        assertThat(timeInterval
                .getIntervalColor())
                .isEqualTo(IntervalColor.WHITE);
    }

    @Test
    @DisplayName("Color gets initialized to white")
    void colorGetsInitializedToWhite() {
        assertThat(new TimeInterval(14, 16)
                .getIntervalColor())
                .isEqualTo(IntervalColor.WHITE);
    }

    @Test
    @DisplayName("Color setter works properly")
    void colorSetterWorksProperly() {
        timeInterval.setIntervalColor(IntervalColor.RED);
        assertThat(timeInterval
                .getIntervalColor())
                .isEqualTo(IntervalColor.RED);
        timeInterval.setIntervalColor(IntervalColor.GREEN);
        assertThat(timeInterval
                .getIntervalColor())
                .isEqualTo(IntervalColor.GREEN);
    }
}