package homework31;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.java.basic.homeworks.homework31.ArrayTasks;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArrayTasksTest {

    @Test
    @DisplayName("базовый пример: [1,2,1,2,2] -> [2,2]")
    void tailAfterLastOne_basicExample() {
        int[] input = {1, 2, 1, 2, 2};
        int[] result = ArrayTasks.tailAfterLastOne(input);
        assertThat(result).containsExactly(2, 2);
    }

    @Test
    @DisplayName("если нет единицы — RuntimeException")
    void tailAfterLastOne_noOnes_throws() {
        int[] input = {2, 2, 2, 2};
        assertThatThrownBy(() -> ArrayTasks.tailAfterLastOne(input))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("does not contain 1");
    }

    @Test
    @DisplayName("если 1 в конце — пустой массив")
    void tailAfterLastOne_oneAtEnd_returnsEmpty() {
        int[] input = {2, 2, 1};
        assertThat(ArrayTasks.tailAfterLastOne(input)).isEmpty();
    }

    @Test
    @DisplayName("null вход — IllegalArgumentException")
    void tailAfterLastOne_null_throwsIAE() {
        assertThatThrownBy(() -> ArrayTasks.tailAfterLastOne(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "CSV {0} => {1}")
    @CsvSource({
            "'1,2',       true",
            "'1,1',       false",
            "'1,3',       false",
            "'1,2,2,1',   true",
            "'2,2,2',     false",
            "'1',         false"
    })
    void onlyOnesAndTwos_paramCsv(String csv, boolean expected) {
        int[] arr = parseCsv(csv);
        boolean actual = ArrayTasks.onlyOnesAndTwosWithBothPresent(arr);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest(name = "MethodSource")
    @MethodSource("validityDataset")
    void onlyOnesAndTwos_paramMethod(int[] arr, boolean expected) {
        boolean actual = ArrayTasks.onlyOnesAndTwosWithBothPresent(arr);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> validityDataset() {
        return Stream.of(
                Arguments.of(new int[]{1, 2}, true),
                Arguments.of(new int[]{2, 1, 2, 1}, true),
                Arguments.of(new int[]{1, 1, 1}, false),
                Arguments.of(new int[]{2, 2, 2}, false),
                Arguments.of(new int[]{1, 2, 3}, false)
        );
    }

    @Test
    @DisplayName("null/пустой — IllegalArgumentException")
    void onlyOnesAndTwos_nullOrEmpty_throwIAE() {
        assertThatThrownBy(() -> ArrayTasks.onlyOnesAndTwosWithBothPresent(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ArrayTasks.onlyOnesAndTwosWithBothPresent(new int[]{}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    //Утилита
    private static int[] parseCsv(String csv) {
        String[] parts = csv.split("\\s*,\\s*");
        int[] out = new int[parts.length];
        for (int i = 0; i < parts.length; i++) out[i] = Integer.parseInt(parts[i]);
        return out;
    }
}