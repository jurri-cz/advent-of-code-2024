import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Part2Test {

    @Test
    fun `Sum of wide GPS sample`() {
        val answer = Day15Part2("day-1501-sample.txt").part2()
        assertEquals(9021,  answer)
    }

    @Test
    fun `Sum of wide GPS sample 2`() {
        val answer = Day15Part2("day-1501-sample2.txt").part2()
        assertEquals(1751,  answer)
    }

    @Test
    fun `Sum of wide GPS sample 3`() {
        val answer = Day15Part2("day-1501-sample3.txt").part2()
        assertEquals(618,  answer)
    }

    @Test
    fun `Sum of wide GPS final`() {
        val answer = Day15Part2("day-1501.txt").part2()
        assertEquals(1509724,  answer)
    }

}