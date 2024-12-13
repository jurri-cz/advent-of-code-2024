import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {

    @Test
    fun `Checksum sample`() {
        val answer = Day09("day-0901-sample.txt").part1()
        assertEquals(1928L,  answer)
    }

    @Test
    fun `Checksum final`() {
        val answer = Day09("day-0901.txt").part1()
        assertEquals(6331212425418L,  answer)
    }

    @Test
    fun `Defragment checksum sample`() {
        val answer = Day09("day-0901-sample.txt").part2()
        assertEquals(2858L,  answer)
    }

    @Test
    fun `Defragment checksum final`() {
        val answer = Day09("day-0901.txt").part2()
        assertEquals(6363268339304L,  answer)
    }

}