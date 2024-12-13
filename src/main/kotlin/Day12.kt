class Day12(private val file: String) {

    private fun loadInput(): List<List<Char>> {
        return file.fileLines()
            .map { row -> row.map { crop -> crop } }
    }

    data class Plot(val x: Int, val y: Int, val neighbors: Int)
    data class Field(val crop: Char, val plots: MutableList<Plot> = mutableListOf()) {
        private val area: Int
            get() = plots.size

        private val perimeter: Int
            get() = plots.sumOf { plot ->
                4 - plot.neighbors
            }

        private val corners: Int
            get() {
                val plotCoords = plots.map { it.x to it.y }.toSet()

                val cornerPlots = plotCoords.flatMap { (x, y) ->
                    listOfNotNull(
                        corner(x, y, 1, 1, plotCoords),
                        corner(x, y, 1, -1, plotCoords),
                        corner(x, y, -1, 1, plotCoords),
                        corner(x, y, -1, -1, plotCoords),
                    )
                }

                return cornerPlots.toSet().size
            }

        val fencePrice
            get() = area * perimeter

        val discountFencePrice
            get() = area * corners

        private fun corner(x: Int, y: Int, dx: Int, dy: Int, plots: Set<Pair<Int, Int>>): Triple<Int,Int,Int>? {
            val horizontal = plots.contains(x+dx to y)
            val vertical = plots.contains(x to y+dy)
            val diagonal = plots.contains(x+dx to y+dy)

            val count = listOf(horizontal, vertical, diagonal).count { it }

            return if (count == 0 || count == 2) {
                Triple(2*x + dx, 2*y + dy, 0)
            } else if (count == 1 && diagonal) {
                Triple(2*x + dx, 2*y + dy, 2*dx + dy)
            } else {
                null
            }
        }

    }

    private fun floodFill(x: Int, y: Int, field: Field, map: List<List<Char>>, xRange: IntRange, yRange: IntRange, toVisit: MutableSet<Pair<Int, Int>>) {
        toVisit.remove(x to y)

        val crop = field.crop

        val neighbors = listOf(
            (x+1 to y),
            (x-1 to y),
            (x to y+1),
            (x to y-1)
        ).mapNotNull { it.takeIf { (xn, yn)-> xn in xRange && yn in yRange && map[yn][xn] == crop } }

        neighbors.forEach { neighbor ->
            if (neighbor in toVisit) {
                val (xn, yn) = neighbor
                floodFill(x = xn, y = yn, field = field, map = map, xRange = xRange, yRange = yRange, toVisit = toVisit)
            }
        }

        field.plots.add(Plot(x, y, neighbors.size))
    }

    private fun mapFields(): List<Field> {
        val plotMap = loadInput()

        val toVisit: MutableSet<Pair<Int, Int>> = plotMap.indices.flatMap { y ->
            plotMap.first().indices.map { x ->
                x to y
            }
        }.toMutableSet()

        val fields: MutableList<Field> = mutableListOf()

        val xRange = plotMap.first().indices
        val yRange = plotMap.indices

        while (toVisit.isNotEmpty()) {
            val (x, y) = toVisit.first()
            val crop = plotMap[y][x]
            val field = Field(crop)
            floodFill(x = x, y = y, field = field, map = plotMap, xRange = xRange, yRange = yRange, toVisit = toVisit)
            fields.add(field)
        }

        return fields.toList()
    }

   fun part1(): Int {
        val fields = mapFields()
        return fields.sumOf { it.fencePrice }
    }

    fun part2(): Int {
        val fields = mapFields()
        return fields.sumOf { it.discountFencePrice }
    }
}