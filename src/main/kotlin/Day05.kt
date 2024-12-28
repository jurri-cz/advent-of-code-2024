class Day05(private val file: String) {
    private fun loadInput(): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val lines = file.fileLines()

        val rules = lines
            .takeWhile { it.isNotEmpty() }
            .map { line ->
                val order = line.split("|")
                order[0].toInt() to order[1].toInt()
            }

        val prints = lines
            .drop(rules.size + 1)
            .map { line -> line.split(",").map { it.toInt() } }

        return rules to prints
    }

    fun part1(): Int {
        val (rules, prints) = loadInput()

        val correctPrints = prints.filter { print ->
            val activeRules = rules.filter { print.containsAll(it.toList()) }

            activeRules.all { rule ->
                val (a, b) = rule
                print.indexOf(a) < print.indexOf(b)
            }
        }

        return correctPrints.sumOf {
            it[it.size / 2]
        }
    }

    fun part2(): Int {
        val (rules, prints) = loadInput()

        val incorrectPrints = prints.filter { print ->
            val activeRules = rules.filter { print.containsAll(it.toList()) }

            activeRules.any { rule ->
                val (a, b) = rule
                print.indexOf(a) > print.indexOf(b)
            }
        }

        val correctedPrints = incorrectPrints.map { print ->
            val activeRules = rules.filter { print.containsAll(it.toList()) }

            print.sortedWith { a, b ->
                if (activeRules.contains(a to b)) {
                    -1
                } else if (activeRules.contains(b to a)) {
                    1
                } else {
                    0
                }
            }
        }

        return correctedPrints.sumOf {
            it[it.size / 2]
        }
    }
}

