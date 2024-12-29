import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day17Test {

    @Test
    fun `Computer sample`() {
        val answer = Day17("day-1701-sample.txt").part1()
        assertEquals("4,6,3,5,6,3,5,2,1,0",  answer)
    }

    @Test
    fun `Computer final`() {
        val answer = Day17("day-1701.txt").part1()
        assertEquals("7,3,0,5,7,1,4,0,5",  answer)
    }

    @Test
    fun `Computer fixed sample`() {
        val answer = Day17("day-1701-sample2.txt").part2()
        assertEquals(117440L,  answer)
    }

    @Test
    fun `Computer fixed final`() {
        val answer = Day17("day-1701.txt").part2()
        assertEquals(202972175280682L,  answer)
    }
}