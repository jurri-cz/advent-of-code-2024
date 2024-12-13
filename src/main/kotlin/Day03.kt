class Day03(private val file: String) {

    class StateMachine(val supportDisabling: Boolean = false) {
        var sum: Int = 0
        var ignoreMuls = false

        private val supportedFunctions
            get() = if (supportDisabling) {
                if (ignoreMuls) {
                    listOf("do()")
                } else {
                    listOf("mul(", "don't()")
                }
            } else {
                listOf("mul(")
            }

        private var state: State = FunctionStart()
        private var args: MutableList<Int> = mutableListOf()

        fun accept(text: String) {
            text.forEach {
                state = state.accept(it)
            }
        }

        private fun reset(char: Char? = null): State {
            state = FunctionStart()
            args = mutableListOf()

            return char?.let { state.accept(it) } ?: state
        }

        private interface State {
            fun accept(char: Char): State
        }

        private inner class Argument(
            private val stopChar: Char,
            private val maxDigits: Int = 3,
            private val onStop: (Int) -> State
        ) : State {
            var text = ""
            override fun accept(char: Char): State {
                return when {
                    text.length < maxDigits && char.isDigit() -> {
                        text += char
                        this
                    }

                    char == stopChar -> onStop(text.toInt())
                    else -> reset(char)
                }
            }
        }

        private inner class FunctionStart : State {
            private val expectations = supportedFunctions.toMutableSet()
            private var index = 0

            override fun accept(char: Char): State {
                expectations.removeIf { index == it.length || it[index] != char }

                if (expectations.isEmpty()) {
                    return reset(if (index == 0) null else char)
                }

                index++

                if (expectations.size != 1) {
                    return this
                }

                val expectation = expectations.first()

                return if (index == expectation.length) {
                    when (expectation) {
                        "mul(" -> Argument(',') {
                            args.add(it)
                            Argument(')') {
                                args.add(it)
                                sum += args.reduce { acc, i -> acc * i }
                                reset()
                            }
                        }

                        "do()" -> {
                            ignoreMuls = false
                            reset()
                        }

                        "don't()" -> {
                            ignoreMuls = true
                            reset()
                        }

                        else -> reset(char)
                    }
                } else {
                    this
                }
            }
        }
    }

    private fun loadInput() = file.fileText()

    fun part1(): Int {
        val input = loadInput()
        val machine = StateMachine()

        machine.accept(input)

        return machine.sum
    }

    fun part2(): Int {
        val input = loadInput()
        val machine = StateMachine(supportDisabling = true)

        machine.accept(input)

        return machine.sum
    }
}

