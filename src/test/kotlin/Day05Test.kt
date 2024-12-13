import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day05Test {

    @Test
    fun `Middle page sample`() {
        val answer = Day05("day-0501-sample.txt").part1()
        assertEquals(143,  answer)
    }

    @Test
    fun `Middle page final`() {
        val answer = Day05("day-0501.txt").part1()
        assertEquals(5639,  answer)
    }

    @Test
    fun `Middle page fixed sample`() {
        val answer = Day05("day-0501-sample.txt").part2()
        assertEquals(123,  answer)
    }

    @Test
    fun `Middle page fixed final`() {
        val answer = Day05("day-0501.txt").part2()
        assertEquals(5273,  answer)
    }
}