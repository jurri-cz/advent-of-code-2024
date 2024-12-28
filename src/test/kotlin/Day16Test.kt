import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {

    @Test
    fun `Maze sample`() {
        val answer = Day16("day-1601-sample.txt").part1()
        assertEquals(7036,  answer)
    }

    @Test
    fun `Maze sample2`() {
        val answer = Day16("day-1601-sample2.txt").part1()
        assertEquals(11048,  answer)
    }

    @Test
    fun `Maze final`() {
        val answer = Day16("day-1601.txt").part1()
        assertEquals(101492,  answer)
    }

    @Test
    fun `Seats sample`() {
        val answer = Day16("day-1601-sample.txt").part2()
        assertEquals(45,  answer)
    }

    @Test
    fun `Seats sample2`() {
        val answer = Day16("day-1601-sample2.txt").part2()
        assertEquals(64,  answer)
    }

    @Test
    fun `Seats final`() {
        val answer = Day16("day-1601.txt").part2()
        assertEquals(543,  answer)
    }

}