import kotlin.math.abs

class Day01(private val file: String) {
    data class ListComparison(
        val left: List<Int>,
        val right: List<Int>
    ) {
        fun distance() = left.sorted().zip(right.sorted())
            .sumOf { abs(it.first - it.second) }

        fun similarity(): Int {
            val rightHistogram = right.groupingBy { it }.eachCount()
            return left.sumOf { it * rightHistogram.getOrDefault(it, 0) }
        }
    }

    private fun  loadComparison(): ListComparison {
        val lines = file.fileLines()

        val pairs = lines
            .map { it.split("\\s+".toRegex()) }
            .map { it[0].toInt() to it[1].toInt() }

        return ListComparison(
            left = pairs.map { it.first },
            right = pairs.map { it.second }
        )
    }

    fun part1(): Int {
        val comparison = loadComparison()
        return comparison.distance()
    }

    fun part2(): Int {
        val comparison = loadComparison()
        return comparison.similarity()
    }
}

