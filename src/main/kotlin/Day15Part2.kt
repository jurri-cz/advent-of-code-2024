class Day15Part2(private val file: String) {

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

    data class Move(val block: Int, val from: Position, val to: Position)

    class Warehouse(
        private var robot: Position,
        private val rowWise: Array<IntArray>,
    )  {

        private fun movesHorizontal(instruction: Instruction, position: Position): List<Move> {
            val block = rowWise[position.y][position.x]
            val nextPosition = position.move(instruction)
            val nextBlock = rowWise[nextPosition.y][nextPosition.x]
            val move = Move(block, position, nextPosition)

            return when (nextBlock) {
                SPACE -> listOf(move)
                WALL -> emptyList()
                LEFT_BOX, RIGHT_BOX -> moves(instruction, nextPosition).takeIf { it.isNotEmpty() }?.plus(move) ?: emptyList()
                else -> return emptyList()
            }

        }

        private fun movesVertical(instruction: Instruction, position: Position): List<Move> {
            val block = rowWise[position.y][position.x]

            val nextPosition = position.move(instruction)
            val currentMove = Move(block, position, nextPosition)

            return when (rowWise[nextPosition.y][nextPosition.x]) {
                SPACE -> listOf(currentMove)
                WALL -> emptyList()
                LEFT_BOX -> {
                    val leftPart = movesVertical(instruction, nextPosition)
                    val rightPart = movesVertical(instruction, nextPosition.move(Instruction.RIGHT))

                    if (leftPart.isEmpty() || rightPart.isEmpty()) {
                        return emptyList()
                    }

                    leftPart + rightPart + currentMove
                }
                RIGHT_BOX -> {
                    val leftPart = movesVertical(instruction, nextPosition.move(Instruction.LEFT))
                    val rightPart = movesVertical(instruction, nextPosition)

                    if (leftPart.isEmpty() || rightPart.isEmpty()) {
                        return emptyList()
                    }

                    leftPart + rightPart + currentMove
                }
                else -> return emptyList()
            }

        }

        private fun moves(instruction: Instruction, position: Position): List<Move> = when(instruction) {
                Instruction.LEFT, Instruction.RIGHT -> movesHorizontal(instruction, position)
                Instruction.UP, Instruction.DOWN -> movesVertical(instruction, position)
            }

        fun feed(instruction: Instruction) {
            val moves = moves(instruction, robot)

            moves.forEach { (_, from, _) ->
                rowWise[from.y][from.x] = SPACE
            }

            moves.forEach { (block, _, to) ->
                rowWise[to.y][to.x] = block
                if (block == ROBOT) {
                    robot = to
                }
            }
        }

        fun gps(): Int {
            return rowWise.flatMapIndexed { y, row ->
                row.mapIndexed { x, cell ->
                    if (cell == LEFT_BOX) y * 100 + x else 0
                }
            }.sum()
        }

        override fun toString(): String {
            return rowWise.joinToString("\n") { row ->
                row.joinToString("") { cell ->
                    when (cell) {
                        SPACE -> " "
                        LEFT_BOX -> "["
                        RIGHT_BOX -> "]"
                        WALL -> "#"
                        ROBOT -> "@"
                        else -> throw IllegalArgumentException("Invalid cell: $cell")
                    }
                }
            }
        }

        companion object {
            const val SPACE = 0
            const val LEFT_BOX = 1
            const val RIGHT_BOX = 2
            const val WALL = 3
            const val ROBOT = 4

            fun from(lines: List<String>): Warehouse {
                lateinit var robot: Position
                val rowWise = lines.mapIndexed { y, row ->
                    row.flatMapIndexed { x, character ->
                        when (character) {
                            '.' -> listOf(SPACE, SPACE)
                            '#' -> listOf(WALL, WALL)
                            'O' -> listOf(LEFT_BOX, RIGHT_BOX)
                            '@' -> {
                                robot = Position(2*x, y)
                                listOf(ROBOT, SPACE)
                            }
                            else -> throw IllegalArgumentException("Invalid character: $character")
                        }
                    }.toIntArray()
                }.toTypedArray()

                return Warehouse(robot, rowWise)
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

    fun part2(): Int {
        val (warehouse, instructions) = loadInput()

        instructions.forEach {
           warehouse.feed(it)
        }

        return warehouse.gps()
    }

}