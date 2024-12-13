import kotlin.math.absoluteValue
import kotlin.math.sign

class Day02(private val file: String) {
    data class Report(
        val levels: List<Int>,
    ) {
        private val Pair<Int,Int>.diff get() = first - second
        private val Int.safe get() = absoluteValue <= 3

        private fun <T> List<T>.exceptIndex(index: Int) = slice(indices - index)

        val pairs: List<Pair<Int, Int>> by lazy {  levels.zipWithNext() }
        val diffs: List<Int> by lazy { pairs.map { it.diff } }

        val safe: Boolean by lazy {
            diffs.all { it.safe } && diffs.sumOf { it.sign }.absoluteValue == diffs.size
        }

        val dampenedSafe: Boolean by lazy {
            if (safe) {
                return@lazy true
            }

            for (index in levels.indices) {
                if (Report(levels.exceptIndex(index)).safe) {
                    return@lazy true
                }
            }

            return@lazy false
        }
    }

    private fun  loadReports(): List<Report> {
        val lines = file.fileLines()

        return lines
            .map {
                Report(
                    it.split(" ").map { it.toInt() }
                )
            }
    }

    fun part1(): Int {
        val reports = loadReports()
        return reports.count { it.safe }
    }

    fun part2(): Int {
        val reports = loadReports()
        return reports.count { it.dampenedSafe }
    }
}

