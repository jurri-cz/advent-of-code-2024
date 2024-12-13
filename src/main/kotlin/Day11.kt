import kotlin.math.log10
import kotlin.math.pow

class Day11(private val file: String) {

    private fun loadInput(): List<Long> {
        return file.fileText()
            .split(" ")
            .map { it.toLong() }
    }

    private fun Long.digits(): Int {
        return if (this == 0L) 1 else (log10(this.toDouble()) + 1).toInt()
    }

    private fun children(number: Long): List<Long> =
        if (number == 0L) {
            listOf(1L)
        } else {
            val digits = number.digits()
            if (digits % 2 == 0) {
                val half = 10.0.pow(digits / 2).toLong()
                listOf(number.div(half), number.rem(half))
            } else {
                listOf(number * 2024L)
            }
        }

    fun part1(iterations: Int = 25): Long {
        val stones = loadInput()
        var counts = stones.associateWith { 1L }

        repeat(iterations) {
            val newCounts = mutableMapOf<Long, Long>()
            counts.forEach { (number, count) ->
                for (child in children(number)) {
                    newCounts.merge(child, count) { a, b -> a + b }
                }
            }
            counts = newCounts
        }

        return counts.values.sum()
    }

    fun part2(): Long {
        return part1(75)
    }
}