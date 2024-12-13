import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {

    @Test
    fun `Stones after 25 blinks sample`() {
        val answer = Day11("day-1101-sample.txt").part1()
        assertEquals(55312L,  answer)
    }

    @Test
    fun `Stones after 25 blinks final`() {
        val answer = Day11("day-1101.txt").part1()
        assertEquals(220722L,  answer)
    }

    @Test
    fun `Stones after 75 blinks final`() {
        val answer = Day11("day-1101.txt").part2()
        assertEquals(261952051690787L,  answer)
    }

}