import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {

    @Test
    fun `Minimal prize cost sample`() {
        val answer = Day13("day-1301-sample.txt").part1()
        assertEquals(480L,  answer)
    }

    @Test
    fun `Minimal prize cost final`() {
        val answer = Day13("day-1301.txt").part1()
        assertEquals(35997L,  answer)
    }

    @Test
    fun `Minimal further prize cost sample`() {
        val answer = Day13("day-1301-sample.txt").part2()
        assertEquals(875318608908L,  answer)
    }

    @Test
    fun `Minimal further prize cost final`() {
        val answer = Day13("day-1301.txt").part2()
        assertEquals(82510994362072L,  answer)
    }
}