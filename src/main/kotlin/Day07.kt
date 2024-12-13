import kotlin.math.log10
import kotlin.math.pow

class Day07(private val file: String) {

    private fun loadInput(): List<Pair<Long, List<Long>>> {
        return file.fileLines()
            .map {
                val tokens = it.split(": ")
                tokens[0].toLong() to tokens[1].split(" ").map(String::toLong)
            }
    }

    private fun solvable(remaining: Long, operands: List<Long>): Boolean {
        if (operands.isEmpty()) {
            return remaining == 0L
        }

        val operand = operands[0]

        if (remaining < operand) {
            return false
        }

        return solvable(remaining - operand, operands.drop(1))
                || (remaining.rem(operand) == 0L && solvable(remaining / operand, operands.drop(1)))
    }

    fun part1(): Long {
        val equations = loadInput()
        return equations
            .filter { solvable(it.first, it.second.reversed()) }
            .sumOf { it.first }
    }

    private fun join(left: Long, right: Long): Long {
        return when {
                                   right < 10L -> left * 10L + right
                                  right < 100L -> left * 100L + right
                                right < 1_000L -> left * 1_000L + right
                               right < 10_000L -> left * 10_000L + right
                              right < 100_000L -> left * 100_000L + right
                            right < 1_000_000L -> left * 1_000_000L + right
                           right < 10_000_000L -> left * 10_000_000L + right
                          right < 100_000_000L -> left * 100_000_000L + right
                        right < 1_000_000_000L -> left * 1_000_000_000L + right
                       right < 10_000_000_000L -> left * 10_000_000_000L + right
                      right < 100_000_000_000L -> left * 100_000_000_000L + right
                    right < 1_000_000_000_000L -> left * 1_000_000_000_000L + right
                   right < 10_000_000_000_000L -> left * 10_000_000_000_000L + right
                  right < 100_000_000_000_000L -> left * 100_000_000_000_000L + right
                right < 1_000_000_000_000_000L -> left * 1_000_000_000_000_000L + right
               right < 10_000_000_000_000_000L -> left * 10_000_000_000_000_000L + right
              right < 100_000_000_000_000_000L -> left * 100_000_000_000_000_000L + right
            right < 1_000_000_000_000_000_000L -> left * 1_000_000_000_000_000_000L + right
           else -> 10.00.pow(log10(right.toDouble()).toInt() + 1).toLong() * left + right
        }
    }

    private fun solvable2(expected: Long, current: Long, operands: List<Long>): Boolean {
        if (operands.isEmpty()) {
            return current == expected
        }

        if (current > expected) {
            return false
        }

        val operand = operands[0]
        val restOfOperands = operands.drop(1)

        return solvable2(expected, current + operand, restOfOperands)
                || solvable2(expected, current * operand, restOfOperands)
                || (current > 0 && solvable2(expected, join(current, operand), restOfOperands))
    }

    fun part2(): Long {
        val equations = loadInput()
        return equations
            .filter {
                solvable2(it.first, 0, it.second)
            }
            .sumOf { it.first }
    }
}
