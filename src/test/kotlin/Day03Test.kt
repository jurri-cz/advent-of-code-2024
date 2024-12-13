import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun `Sum of multiplications sample`() {
        val answer = Day03("day-0301-sample.txt").part1()
        assertEquals(161,  answer)
    }

    @Test
    fun `Sum of multiplications final`() {
        val answer = Day03("day-0301.txt").part1()
        assertEquals(187833789, answer)
    }

    @Test
    fun `Sum of multiplications with disabling sample`() {
        val answer = Day03("day-0302-sample.txt").part2()
        assertEquals(48,  answer)
    }

    @Test
    fun `Sum of multiplications with disabling final`() {
        val answer = Day03("day-0301.txt").part2()
        assertEquals(94455185, answer)
    }

}