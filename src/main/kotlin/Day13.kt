class Day13(private val file: String) {

    data class Prize(val x: Long, val y: Long)

    data class Button(val cost: Long, val dx: Long, val dy: Long) {
        companion object {
            fun from(cost: Long, line: String): Button {
                val (dx, dy) = "X\\+(\\d+), Y\\+(\\d+)$".toRegex().find(line)?.destructured
                    ?: throw IllegalArgumentException("Invalid button line: $line")

                return Button(cost, dx.toLong(), dy.toLong())
            }
        }
    }

    data class Machine(val a: Button, val b: Button, val prize: Prize) {
        companion object {
            fun from(a: String, b: String, prize: String): Machine {
                val buttonA = Button.from(3, a)
                val buttonB = Button.from(1, b)
                val (x, y) = "X=(\\d+), Y=(\\d+)$".toRegex().find(prize)?.destructured
                    ?: throw IllegalArgumentException("Invalid prize line: $prize")

                return Machine(buttonA, buttonB, Prize(x.toLong(), y.toLong()))
            }
        }
    }

    private fun loadInput(): List<Machine> {
        return file.fileLines()
            .filterNot { it.isBlank() }
            .chunked(3)
            .map { (a, b, prize) ->
                Machine.from(a, b, prize)
            }
    }

    fun part1(dPrize: Long = 0L): Long {
        val machines = loadInput()

        return machines.sumOf {
            val (a, b, basePrize) = it

            val prize = Prize(basePrize.x + dPrize, basePrize.y + dPrize)

            val bNumerator = (a.dx*prize.y - a.dy*prize.x)
            val bDenominator = (a.dx*b.dy - a.dy*b.dx)

            if (bNumerator % bDenominator != 0L) {
                return@sumOf 0L
            }

            val bClicks = bNumerator / bDenominator

            val aNumerator = (prize.x - b.dx*bClicks)
            val aDenominator = a.dx

            if (aNumerator % aDenominator != 0L) {
                return@sumOf 0L
            }

            val aClicks = aNumerator / aDenominator

            return@sumOf 3*aClicks + bClicks

        }
    }

    fun part2(): Long {
        return part1(dPrize = 10000000000000L)
    }
}