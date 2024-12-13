class Day04(private val file: String) {
    private val shapes1 =
        listOf(
            listOf(
                listOf('X', 'M', 'A', 'S')
            ),
            listOf(
                listOf('S', 'A', 'M', 'X')
            ),
            listOf(
                listOf('X'),
                listOf('M'),
                listOf('A'),
                listOf('S'),
            ),
            listOf(
                listOf('S'),
                listOf('A'),
                listOf('M'),
                listOf('X'),
            ),
            listOf(
                listOf('X', null, null, null),
                listOf(null, 'M', null, null),
                listOf(null, null, 'A', null),
                listOf(null, null, null, 'S'),
            ),
            listOf(
                listOf('S', null, null, null),
                listOf(null, 'A', null, null),
                listOf(null, null, 'M', null),
                listOf(null, null, null, 'X'),
            ),
            listOf(
                listOf(null, null, null, 'S'),
                listOf(null, null, 'A', null),
                listOf(null, 'M', null, null),
                listOf('X', null, null, null),
            ),
            listOf(
                listOf(null, null, null, 'X'),
                listOf(null, null, 'M', null),
                listOf(null, 'A', null, null),
                listOf('S', null, null, null),
            ),
        )

    private val shapes2 =
        listOf(
            listOf(
                listOf('M', null, 'M'),
                listOf(null, 'A', null),
                listOf('S', null, 'S'),
            ),
            listOf(
                listOf('S', null, 'S'),
                listOf(null, 'A', null),
                listOf('M', null, 'M'),
            ),
            listOf(
                listOf('M', null, 'S'),
                listOf(null, 'A', null),
                listOf('M', null, 'S'),
            ),
            listOf(
                listOf('S', null, 'M'),
                listOf(null, 'A', null),
                listOf('S', null, 'M'),
            ),
        )


    private fun loadInput(): List<List<Char>> {
        val lines = file.fileLines()

        return lines.map { line ->
            line.map { it }
        }
    }

    /**
     * Detects the number of times a shape appears in the input.
     * @param shape The shape to detect. nulls are treated as wildcards.
     */
    fun List<List<Char>>.detectShapeCount(shape: List<List<Char?>>): Int {
        var count = 0

        val xIndices = (0..this.size - shape.size)
        val yIndices = (0..this[0].size - shape[0].size)

        for (x in xIndices) {
            for (y in yIndices) {
                val cutout = this
                    .slice(x until x + shape.size)
                    .mapIndexed { shapeX, row ->
                        row
                            .slice(y until y + shape[0].size)
                            .mapIndexed { shapeY, value ->
                                if (shape[shapeX][shapeY] == null) {
                                    null
                                } else {
                                    value
                                }
                            }
                    }
                if (cutout == shape) {
                    count = count.inc()
                }
            }
        }

        return count
    }

    fun part1(): Int {
        val input = loadInput()

        return shapes1.sumOf {
            input.detectShapeCount(it)
        }
    }

    fun part2(): Int {
        val input = loadInput()

        return shapes2.sumOf {
            input.detectShapeCount(it)
        }
    }
}

