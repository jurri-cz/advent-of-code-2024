class Day08(private val file: String) {

    private fun loadInput(): List<String> {
        return file.fileLines()
    }

    fun part1(): Int {
        val map = loadInput()

        val frequencyAntennas = map
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, char -> char to (x to y) }
            }
            .groupBy { point -> point.first }
            .mapValues { point -> point.value.map { it.second } }
            .filter { it.key != '.' }

        val boundaries = map[0].length to map.size
        fun Pair<Int,Int>.withinBoundaries(): Boolean {
            val (x, y) = this
            val (width, height) = boundaries
            return x in 0..<width && y in 0..<height
        }

        val frequencyAntinodes = frequencyAntennas
            .filter { it.value.size > 1 }
            .mapValues { it ->
                val antennas = it.value
                antennas
                    .flatMapIndexed { index, (x, y) ->
                        antennas.slice(antennas.indices - index).map { (x2, y2) ->
                            2*x - x2 to 2*y - y2
                        }
                    }
                    .filter { point -> point.withinBoundaries() }
                    .toSet()
            }

        return frequencyAntinodes
            .flatMap { it.value }
            .toSet()
            .size
    }

    fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second
    fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second

    fun part2(): Int {
        val map = loadInput()

        val frequencyAntennas = map
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, char -> char to (x to y) }
            }
            .groupBy { point -> point.first }
            .mapValues { point -> point.value.map { it.second } }
            .filter { it.key != '.' }

        val boundaries = map[0].length to map.size
        fun Pair<Int,Int>.withinBoundaries(): Boolean {
            val (x, y) = this
            val (width, height) = boundaries
            return x in 0..<width && y in 0..<height
        }

        val frequencyAntinodes = frequencyAntennas
            .filter { it.value.size > 1 }
            .mapValues {
                val antennas = it.value
                antennas
                    .flatMapIndexed { index, (x, y) ->
                        antennas
                            .slice(antennas.indices - index)
                            .flatMap { (x2, y2) ->
                                val dx = x - x2
                                val dy = y - y2

                                val nextAntinodes: Set<Pair<Int, Int>> = generateSequence(x to y) { point ->
                                    val next = point.plus(dx to dy)
                                    if (next.withinBoundaries()) next else null
                                }.toSet()

                                val previousAntinodes: Set<Pair<Int, Int>> = generateSequence(x to y) { point ->
                                    val next = point.minus(dx to dy)
                                    if (next.withinBoundaries()) next else null
                                }.toSet()

                                (nextAntinodes + previousAntinodes)
                            }
                    }
                    .filter { point -> point.withinBoundaries() }
                    .toSet()
            }

        return frequencyAntinodes
            .flatMap { it.value }
            .toSet()
            .size
    }
}
