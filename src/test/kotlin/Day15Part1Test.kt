import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Part1Test {

    @Test
    fun `Sum of GPS sample`() {
        val answer = Day15Part1("day-1501-sample.txt").part1()
        assertEquals(10092,  answer)
    }

    @Test
    fun `Sum of GPS sample 2`() {
        val answer = Day15Part1("day-1501-sample2.txt").part1()
        assertEquals(2028,  answer)
    }

    @Test
    fun `Sum of GPS final`() {
        val answer = Day15Part1("day-1501.txt").part1()
        assertEquals(1475249,  answer)
    }

}