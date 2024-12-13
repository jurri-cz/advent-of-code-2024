import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {

    @Test
    fun `Antinode count sample`() {
        val answer = Day08("day-0801-sample.txt").part1()
        assertEquals(14,  answer)
    }

    @Test
    fun `Antinode count final`() {
        val answer = Day08("day-0801.txt").part1()
        assertEquals(259,  answer)
    }

    @Test
    fun `Harmonic antinode count sample`() {
        val answer = Day08("day-0801-sample.txt").part2()
        assertEquals(34,  answer)
    }

    @Test
    fun `Harmonic antinode count final`() {
        val answer = Day08("day-0801.txt").part2()
        assertEquals(927,  answer)
    }

}