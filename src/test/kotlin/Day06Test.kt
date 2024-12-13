import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun `Visited spaces sample`() {
        val answer = Day06("day-0601-sample.txt").part1()
        assertEquals(41,  answer)
    }

    @Test
    fun `Visited spaces final`() {
        val answer = Day06("day-0601.txt").part1()
        assertEquals(4559,  answer)
    }

    @Test
    fun `New obstruction sample`() {
        val answer = Day06("day-0601-sample.txt").part2()
        assertEquals(6,  answer)
    }

    @Test
    fun `New obstruction final`() {
        val answer = Day06("day-0601.txt").part2()
        assertEquals(1604,  answer)
    }

}