class Day10(private val file: String) {

    private fun List<List<Int>>.elementMinus(other: List<List<Int>>): List<List<Int>> {
        return this.zip(other) { a, b ->
            a.zip(b).map { (x, y) -> x - y }
        }
    }

    private fun List<List<Int>>.filterValue(expected: Int, target: Int = 1): List<List<Int>> {
        return this.map { row ->
            row.map { value -> if (value == expected) target else 0 }
        }
    }

    private fun loadInput(): List<List<Int>> {
        return file.fileLines()
            .map { row ->
                row.map { height ->
                    height.digitToInt()
                }
            }
    }

    class Path(val id: Pair<Int, Int>, val value: Int, private val connected: MutableSet<Path> = mutableSetOf()) {
        fun add(path: Path) {
            connected.add(path)
        }

        fun score(visited: MutableSet<Path> = mutableSetOf()): Set<Path> {
            if (visited.contains(this)) {
                return emptySet()
            }

            visited.add(this)

            if (value == 9) {
                return setOf(this)
            }

            return connected.flatMap { it.score(visited) }.toSet()
        }

        fun rating(): Int {
            if (value == 9) {
                return 1
            }

            return connected.sumOf { it.rating() }
        }

        override fun toString(): String {
            return "Path(value=$value, connected=${connected.map { it.id }})"
        }
    }

    private fun loadPaths(): List<List<Path>> {
        val map = loadInput()

        val verticalGradient = map.drop(1).elementMinus(map.dropLast(1))
        val horizontalGradient = map.map { it.drop(1) }.elementMinus(map.map { it.dropLast(1) })

        val north = listOf((0..<verticalGradient[0].size).map { 0 }) + verticalGradient.filterValue(-1)
        val south = verticalGradient.filterValue(1) + listOf((0..<verticalGradient[0].size).map { 0 })
        val west = horizontalGradient.filterValue(-1).map { listOf(0) + it }
        val east = horizontalGradient.filterValue(1).map { it + listOf(0) }

        val paths = map.mapIndexed { y, row ->
            row.mapIndexed { x, value ->
                Path(id = x to y, value = value)
            }
        }

        paths.forEachIndexed { y, row ->
            row.forEachIndexed { x, path ->
                if (north[y][x] > 0) {
                    path.add(paths[y - 1][x])
                }
                if (south[y][x] > 0) {
                    path.add(paths[y + 1][x])
                }
                if (west[y][x] > 0) {
                    path.add(paths[y][x - 1])
                }
                if (east[y][x] > 0) {
                    path.add(paths[y][x + 1])
                }
            }
        }

        return paths
    }

    fun part1(): Int {
        val paths = loadPaths()

        return paths
            .flatten()
            .filter { it.value == 0 }
            .sumOf { it.score().size }
    }

    fun part2(): Int {
        val paths = loadPaths()

        return paths
            .flatten()
            .filter { it.value == 0 }
            .sumOf { it.rating() }
    }
}
