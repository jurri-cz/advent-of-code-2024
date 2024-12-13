import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day02Test {
    @Test
    fun `Number of safe reports sample`() {
        val answer = Day02("day-0201-sample.txt").part1()
        assertEquals(2,  answer)
    }

    @Test
    fun `Sum of safe reports final`() {
        val answer = Day02("day-0201.txt").part1()
        assertEquals(432, answer)
    }

    @Test
    fun `Number of tolerated safe reports sample`() {
        val answer = Day02("day-0201-sample.txt").part2()
        assertEquals(4,  answer)
    }

    @Test
    fun `Number of tolerated safe reports final`() {
        val answer = Day02("day-0201.txt").part2()
        assertEquals(488,  answer)
    }
}