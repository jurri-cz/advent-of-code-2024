import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {

    @Test
    fun `Fence cost sample`() {
        val answer = Day12("day-1201-sample.txt").part1()
        assertEquals(1930,  answer)
    }

    @Test
    fun `Fence cost sample 2`() {
        val answer = Day12("day-1201-sample2.txt").part1()
        assertEquals(140,  answer)
    }

    @Test
    fun `Fence cost final`() {
        val answer = Day12("day-1201.txt").part1()
        assertEquals(1464678,  answer)
    }

    @Test
    fun `Discount fence cost sample`() {
        val answer = Day12("day-1201-sample.txt").part2()
        assertEquals(1206,  answer)
    }

    @Test
    fun `Discount fence cost sample 2`() {
        val answer = Day12("day-1201-sample2.txt").part2()
        assertEquals(80,  answer)
    }

    @Test
    fun `Discount fence cost sample 3`() {
        val answer = Day12("day-1201-sample3.txt").part2()
        assertEquals(236,  answer)
    }

    @Test
    fun `Discount fence cost sample 4`() {
        val answer = Day12("day-1201-sample4.txt").part2()
        assertEquals(368,  answer)
    }

    @Test
    fun `Discount fence cost final`() {
        val answer = Day12("day-1201.txt").part2()
        assertEquals(877492,  answer)
    }

}