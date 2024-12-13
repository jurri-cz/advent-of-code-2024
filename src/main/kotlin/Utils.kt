fun String.fileLines() = ClassLoader.getSystemResourceAsStream(this)!!.bufferedReader().use { it.readLines() }
fun String.fileText() = ClassLoader.getSystemResourceAsStream(this)!!.bufferedReader().use { it.readText() }
