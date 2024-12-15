import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {

    @Test
    fun `Safety factor sample`() {
        val answer = Day14("day-1401-sample.txt").part1(width = 11, height = 7)
        assertEquals(12,  answer)
    }

    @Test
    fun `Safety factor final`() {
        val answer = Day14("day-1401.txt").part1(width = 101, height = 103)
        assertEquals(211773366,  answer)
    }

    @Test
    fun `Print tree final`() {
        Day14("day-1401.txt").part2(width = 101, height = 103, seconds = 0..10000, threshold = 25)
    }

}