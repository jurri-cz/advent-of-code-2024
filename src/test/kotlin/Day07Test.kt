import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {

    @Test
    fun `Solvable sum sample`() {
        val answer = Day07("day-0701-sample.txt").part1()
        assertEquals(3749L,  answer)
    }

    @Test
    fun `Solvable sum final`() {
        val answer = Day07("day-0701.txt").part1()
        assertEquals(1708857123053L,  answer)
    }

    @Test
    fun `Solvable concatenating sum sample`() {
        val answer = Day07("day-0701-sample.txt").part2()
        assertEquals(11387L,  answer)
    }

    @Test
    fun `Solvable concatenating final`() {
        val answer = Day07("day-0701.txt").part2()
        assertEquals(189207836795655L,  answer)
    }

}