import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    @Test
    fun `Sum of smallest distances sample`() {
        val answer = Day01("day-0101-sample.txt").part1()
        assertEquals(11, answer)
    }

    @Test
    fun `Sum of smallest distances final`() {
        val answer = Day01("day-0101.txt").part1()
        assertEquals(2031679, answer)
    }

    @Test
    fun `Location lists similarity sample`() {
        val answer = Day01("day-0101-sample.txt").part2()
        assertEquals(31, answer)
    }

    @Test
    fun `Location lists similarity final`() {
        val answer = Day01("day-0101.txt").part2()
        assertEquals(19678534, answer)
    }
}