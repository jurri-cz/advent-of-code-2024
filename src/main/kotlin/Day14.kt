import kotlin.math.absoluteValue
import kotlin.math.sign

class Day14(private val file: String) {

    data class Vector(val x: Int, val y: Int)

    data class Robot(val position: Vector, val velocity: Vector) {
        fun move(width: Int, height: Int, seconds: Int): Robot {
            return Robot(
                Vector(
                    (width + (position.x + seconds * velocity.x) % width) % width,
                    (height + (position.y + seconds * velocity.y) % height) % height
                ),
                velocity
            )
        }

        fun quadrant(midWidth: Int, midHeight: Int): Int {
            val x = position.x.compareTo(midWidth).sign
                .takeIf { it != 0 }
                ?.let { (it + 1) / 2 }
                ?: return 0

            val y = position.y.compareTo(midHeight).sign
                .takeIf { it != 0 }
                ?.let { (it + 1) / 2 }
                ?: return 0

            return 2 * y + x + 1
        }
    }

    private fun loadInput(): List<Robot> {
        return file.fileLines()
            .map {
                val (x, y, dx, dy) = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)$".toRegex()
                    .find(it)
                    ?.destructured
                    ?: throw IllegalArgumentException("Invalid robot line: $it")

                Robot(Vector(x.toInt(), y.toInt()), Vector(dx.toInt(), dy.toInt()))
            }
    }

    fun part1(width: Int, height: Int, seconds: Int = 100): Int {
        val robotsStart = loadInput()

        val robotsEnd = robotsStart.map {
            it.move(width, height, seconds)
        }

        val midWidth = width / 2
        val midHeight = height / 2

        val quadrants = robotsEnd.groupBy {
            it.quadrant(midWidth, midHeight)
        }

        val robotsPerQuadrant = (1..4).map { quadrants[it]?.size ?: 0 }

        return robotsPerQuadrant.fold(1) { acc, i -> acc * i }
    }

    fun part2(width: Int, height: Int, seconds: IntProgression, threshold: Int) {
        val robotsStart = loadInput()

        val midWidth = width / 2
        val midHeight = height / 2

        seconds.forEach { second ->
            val robots = robotsStart.map { robot ->
                robot.move(width, height, second)
            }

            val avgX = robots.sumOf { it.position.x } / robots.size
            val avgY = robots.sumOf { it.position.y } / robots.size

            // In the end, detecting an off-center was enough, the tree image was no in the center
            if ((midWidth - avgX).absoluteValue * (midHeight - avgY).absoluteValue > threshold) {
                println("Second $second, center: $avgX, $avgY")

                (0..height).forEach { y ->
                    (0..width)
                        .map { x -> robots.count { it.position == Vector(x, y) } }
                        .joinToString(separator = "") { if (it > 0) "#" else "." }
                        .let { println("XX $it XX") }
                }
            }
        }
    }
}