package analyzer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StudentAnalyzerTest {

    private final StudentAnalyzer analyzer = new StudentAnalyzer();

    // ────────────────────────────────────────────────
    // Tests cho countExcellentStudents
    // ────────────────────────────────────────────────

    @Test
    void testCountExcellentStudents_NullList() {
        assertEquals(0, analyzer.countExcellentStudents(null));
    }

    @Test
    void testCountExcellentStudents_OnlyInvalidScores() {
        List<Double> scores = Arrays.asList(-0.1, -5.0, 10.1, 15.0, Double.NaN);
        assertEquals(0, analyzer.countExcellentStudents(scores));
    }

    @Test
    void testCountExcellentStudents_OnlyNonExcellentValidScores() {
        List<Double> scores = Arrays.asList(0.0, 4.5, 7.9, 7.999);
        assertEquals(0, analyzer.countExcellentStudents(scores));
    }

    @Test
    void testCountExcellentStudents_OnlyExcellentScores() {
        List<Double> scores = Arrays.asList(8.0, 9.0, 10.0);
        assertEquals(3, analyzer.countExcellentStudents(scores));
    }

    @Test
    void testCountExcellentStudents_BoundaryValues() {
        List<Double> scores = Arrays.asList(
                -0.001, 0.0, 7.999, 8.0, 8.0001, 9.999, 10.0, 10.001
        );
        assertEquals(4, analyzer.countExcellentStudents(scores)); // 8.0, 8.0001, 9.999, 10.0
    }

    // ────────────────────────────────────────────────
    // Tests cho calculateValidAverage
    // ────────────────────────────────────────────────

    @Test
    void testCalculateValidAverage_NullList() {
        assertEquals(0.0, analyzer.calculateValidAverage(null));
    }

    @Test
    void testCalculateValidAverage_EmptyList() {
        assertEquals(0.0, analyzer.calculateValidAverage(Collections.emptyList()));
    }

    @Test
    void testCalculateValidAverage_OnlyInvalidScores() {
        List<Double> scores = Arrays.asList(-1.0, 10.1, -0.0001, 11.5, Double.NaN);
        assertEquals(0.0, analyzer.calculateValidAverage(scores));
    }

    @Test
    void testCalculateValidAverage_OneValidScore() {
        assertEquals(7.5, analyzer.calculateValidAverage(Collections.singletonList(7.5)));
        assertEquals(0.0, analyzer.calculateValidAverage(Collections.singletonList(0.0)));
        assertEquals(10.0, analyzer.calculateValidAverage(Collections.singletonList(10.0)));
    }

    @ParameterizedTest
    @MethodSource("validAverageProvider")
    void testCalculateValidAverage_VariousValidCases(List<Double> scores, double expected, double delta) {
        assertEquals(expected, analyzer.calculateValidAverage(scores), delta);
    }

    static Stream<Arguments> validAverageProvider() {
        return Stream.of(
                // Chỉ valid scores
                Arguments.of(Arrays.asList(0.0, 5.0, 10.0), 5.0, 0.0001),
                Arguments.of(Arrays.asList(8.0, 9.0, 10.0), 9.0, 0.0001),

                // Mix valid + invalid
                Arguments.of(Arrays.asList(9.5, -1.0, 11.0, 8.2, 7.0), (9.5 + 8.2 + 7.0) / 3, 0.01),

                // Floating point precision
                Arguments.of(Arrays.asList(1.1, 2.2, 3.3), 2.2, 0.0001),

                // All same value
                Arguments.of(Arrays.asList(6.0, 6.0, 6.0), 6.0, 0.0001)
        );
    }

    // Test đã có trong code gốc của bạn (giữ lại để tham chiếu)
    @Test
    void testCalculateValidAverage_OriginalMixCase() {
        assertEquals(
                8.166666..., //  (9.0 + 8.5 + 7.0) / 3
                analyzer.calculateValidAverage(Arrays.asList(9.0, 8.5, 7.0, 11.0, -1.0)),
                0.01
        );
    }
}
