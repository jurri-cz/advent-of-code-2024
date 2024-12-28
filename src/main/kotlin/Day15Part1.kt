class Day15Part1(private val file: String) {

    enum class Instruction {
        UP, DOWN, LEFT, RIGHT
    }

    data class Position(val x: Int, val y: Int) {
        fun move(instruction: Instruction): Position {
            return when (instruction) {
                Instruction.UP -> Position(x, y - 1)
                Instruction.DOWN -> Position(x, y + 1)
                Instruction.LEFT -> Position(x - 1, y)
                Instruction.RIGHT -> Position(x + 1, y)
            }
        }

    }

    class Warehouse(
        width: Int,
        private var robot: Position,
        private val rowWise: Array<IntArray>,
    )  {
        private val columnWise = (0 until width).map { column ->
            rowWise.map { it[column] }.toIntArray()
        }.toTypedArray()

        fun feed(instruction: Instruction) {
            val next = robot.move(instruction)

            with(rowWise[next.y][next.x]) {
                if (this == SPACE) {
                    robot = next
                    return
                }

                if (this == WALL) {
                    return
                }
            }

            val nextSpace: Position? = when (instruction) {
                Instruction.UP -> {
                    columnWise[robot.x]
                        .withIndex()
                        .take(robot.y - 1)
                        .findLast { it.value == SPACE || it.value == WALL }
                        ?.takeIf { it.value == SPACE }
                        ?.let { Position(robot.x, it.index) }
                }
                Instruction.DOWN -> {
                    columnWise[robot.x]
                        .withIndex()
                        .drop(robot.y + 2)
                        .find { it.value == SPACE || it.value == WALL }
                        ?.takeIf { it.value == SPACE }
                        ?.let { Position(robot.x, it.index) }
                }
                Instruction.LEFT -> {
                    rowWise[robot.y]
                        .withIndex()
                        .take(robot.x - 1)
                        .findLast { it.value == SPACE || it.value == WALL }
                        ?.takeIf { it.value == SPACE }
                        ?.let { Position(it.index, robot.y) }
                }
                Instruction.RIGHT -> {
                    rowWise[robot.y]
                        .withIndex()
                        .drop(robot.x + 2)
                        .find { it.value == SPACE || it.value == WALL }
                        ?.takeIf { it.value == SPACE }
                        ?.let { Position(it.index, robot.y) }
                }
            }

            if (nextSpace == null) {
                return
            }

            rowWise[nextSpace.y][nextSpace.x] = BOX
            columnWise[nextSpace.x][nextSpace.y] = BOX

            robot = next

            rowWise[robot.y][robot.x] = SPACE
            columnWise[robot.x][robot.y] = SPACE
        }

        fun gps(): Int {
            return rowWise.flatMapIndexed { y, row ->
                row.mapIndexed { x, cell ->
                    if (cell == BOX) y * 100 + x else 0
                }
            }.sum()
        }

        override fun toString(): String {
            val clone = rowWise.map { it.copyOf() }
            clone[robot.y][robot.x] = ROBOT
            return clone.joinToString("\n") { row ->
                row.joinToString("") { cell ->
                    when (cell) {
                        SPACE -> " "
                        BOX -> "O"
                        WALL -> "#"
                        ROBOT -> "@"
                        else -> throw IllegalArgumentException("Invalid cell: $cell")
                    }
                }
            }
        }

        companion object {
            val SPACE = 0
            val BOX = 1
            val WALL = 2
            val ROBOT = 3

            fun from(lines: List<String>): Warehouse {
                val width = lines[0].length

                lateinit var robot: Position
                val rowWise = lines.mapIndexed { y, row ->
                    row.mapIndexed { x, character ->
                        when (character) {
                            '.' -> SPACE
                            '#' -> WALL
                            'O' -> BOX
                            '@' -> {
                                robot = Position(x, y)
                                SPACE
                            }
                            else -> throw IllegalArgumentException("Invalid character: $character")
                        }
                    }.toIntArray()
                }.toTypedArray()

                return Warehouse(width, robot, rowWise)
            }
        }
    }

    private fun loadInput(): Pair<Warehouse, List<Instruction>> {
        val lines = file.fileLines()

        val warehouseLines = lines.takeWhile { it.isNotEmpty() }
        val warehouse = Warehouse.from(warehouseLines)

        val instructionLine = lines
            .drop(warehouseLines.size + 1)
            .joinToString(separator = "")

        val instructions = instructionLine.map {
            when (it) {
                '^' -> Instruction.UP
                'v' -> Instruction.DOWN
                '<' -> Instruction.LEFT
                '>' -> Instruction.RIGHT
                else -> throw IllegalArgumentException("Invalid instruction: $it")
            }
        }

        return warehouse to instructions
    }

    fun part1(): Int {
        val (warehouse, instructions) = loadInput()

        instructions.forEach(warehouse::feed)

        return warehouse.gps()
    }

}