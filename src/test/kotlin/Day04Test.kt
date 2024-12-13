import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day04Test {

    @Test
    fun `Word count sample`() {
        val answer = Day04("day-0401-sample.txt").part1()
        assertEquals(18,  answer)
    }

    @Test
    fun `Word count final`() {
        val answer = Day04("day-0401.txt").part1()
        assertEquals(2642,  answer)
    }

    @Test
    fun `Shape count sample`() {
        val answer = Day04("day-0401-sample.txt").part2()
        assertEquals(9,  answer)
    }

    @Test
    fun `Shape count final`() {
        val answer = Day04("day-0401.txt").part2()
        assertEquals(1974,  answer)
    }
}