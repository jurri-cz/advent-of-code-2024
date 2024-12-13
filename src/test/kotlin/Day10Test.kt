import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {

    @Test
    fun `Trailhead score sum sample`() {
        val answer = Day10("day-1001-sample.txt").part1()
        assertEquals(36,  answer)
    }

    @Test
    fun `Trailhead score sum final`() {
        val answer = Day10("day-1001.txt").part1()
        assertEquals(776,  answer)
    }

    @Test
    fun `Trailhead rating sum sample`() {
        val answer = Day10("day-1001-sample.txt").part2()
        assertEquals(81,  answer)
    }

    @Test
    fun `Trailhead rating sum final`() {
        val answer = Day10("day-1001.txt").part2()
        assertEquals(1657,  answer)
    }

}