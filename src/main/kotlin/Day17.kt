import kotlin.math.pow

class Day17(private val file: String) {

    data class Computer(
        val initialA: Long = 0L,
        val initialB: Long = 0L,
        val initialC: Long = 0L,
        val program: List<Long>
    ) {
        var a: Long = initialA
        var b: Long = initialB
        var c: Long = initialC
        var pointer: Int = 0
        var output: MutableList<Long> = mutableListOf()
        var expectedOutputIndex = 0

        fun run(customA: Long? = null) {
            a = customA ?: initialA
            b = initialB
            c = initialC
            pointer = 0
            expectedOutputIndex = 0
            output = mutableListOf()
            while (pointer < program.size) {
                val opcode = program[pointer]
                val operand = program[pointer+1]
                pointer += 2
                call(opcode, operand)
            }
        }

        private fun combo(operand: Long) = when(operand) {
            0L,1L,2L,3L -> operand
            4L -> a
            5L -> b
            6L -> c
            else -> throw IllegalArgumentException("Unknown combo operand value $operand")
        }

        private fun adv(operand: Long) {
            a = a shr combo(operand).toInt()
        }

        private fun bxl(operand: Long) {
            b = b xor operand
        }

        private fun bst(operand: Long) {
            b = combo(operand) % 8
        }

        private fun jnz(operand: Long) {
            if (a != 0L) {
                pointer = operand.toInt()
            }
        }

        private fun bxc(operand: Long) {
            b = b xor c
        }

        private fun out(operand: Long) {
            output.add(combo(operand) % 8L)
        }

        private fun bdv(operand: Long) {
            b = a shr combo(operand).toInt()
        }

        private fun cdv(operand: Long) {
            c = a shr combo(operand).toInt()
        }

        private fun call(opcode: Long, operand: Long) {
            when(opcode) {
                0L -> adv(operand)
                1L -> bxl(operand)
                2L -> bst(operand)
                3L -> jnz(operand)
                4L -> bxc(operand)
                5L -> out(operand)
                6L -> bdv(operand)
                7L -> cdv(operand)
                else -> throw IllegalArgumentException("Unknown opcode $opcode")
            }
        }

        override fun toString(): String {
            return """
                |Computer(a=$a, b=$b, c=$c)
                |Program:
                |${
                program.zipWithNext().joinToString("\n") { (opcode, operand) ->
                    when (opcode) {
                        0L -> "adv($operand)"
                        1L -> "bxl($operand)"
                        2L -> "bst($operand)"
                        3L -> "jnz($operand)"
                        4L -> "bxc($operand)"
                        5L -> "out($operand)"
                        6L -> "bdv($operand)"
                        7L -> "cdv($operand)"
                        else -> "err($opcode)"
                    }
                }
            }
            """.trimMargin()
        }
    }

    fun load(): Computer {
        val lines = file.fileLines()

        val a = lines[0].substringAfter(": ").toLong()
        val b = lines[1].substringAfter(": ").toLong()
        val c = lines[2].substringAfter(": ").toLong()

        val program = lines[4].substringAfter(": ").split(",").map { it.toLong() }

        return Computer(a, b, c, program)
    }

    fun part1(): String {
        val computer = load()
        computer.run()
        return computer.output.joinToString(",")
    }

    fun part2(): Long {
        val computer = load()

        println(computer)

        val minA = 8.0.pow(computer.program.size-1).toLong()
        val maxA = 8.0.pow(computer.program.size).toLong()

        var step = 8.0.pow(computer.program.size-1).toLong()
        var spans = listOf(minA ..<maxA)

        for (index in (computer.program.size-1) downTo 0) {
            val nextSpans = mutableListOf<LongRange>()
            for (span in spans) {
                for (a in span step step) {
                    computer.run(customA = a)

                    if (computer.program == computer.output) {
                        return a
                    }

                    if (computer.program.size == computer.output.size && computer.program[index] == computer.output[index]) {
                        nextSpans += a..<(a+step)
                    }
                }
            }

            step /= 8
            spans = nextSpans
        }

        throw IllegalArgumentException("No solution found")
    }

}