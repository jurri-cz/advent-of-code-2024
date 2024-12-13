class Day09(private val file: String) {

    private fun loadInput(): String {
        return file.fileText()
    }

    data class Block(val fileId: Int, val index: Int, val length: Int) {
        val isFile = fileId != -1
        val checksum: Long by lazy {
            if (isFile && length > 0) {
                val start = index.toLong()
                val end = (index + length - 1).toLong()
                fileId.toLong() * (end * end + end - start * start + start) / 2L
            } else {
                0L
            }
        }
    }

    fun MutableList<Block>.removeLastUntil(predicate: (Block) -> Boolean): Block? {
        var block: Block?

        do {
            block = removeLastOrNull()
        } while (block != null && !predicate(block))

        return block
    }

    fun part1(): Long {
        val diskMap = loadInput()
            .map { it.digitToInt() }

        var index = 0
        val blocks = diskMap
            .mapIndexed { fileIndex, length ->
                val fileId = if (fileIndex % 2 == 0) {
                    fileIndex / 2
                } else {
                    -1
                }

                Block(fileId, index, length)
                    .also { index += length }
            }
            .filter { it.length > 0 }


        val compressedBlocks: MutableList<Block> = mutableListOf()
        val buffer = blocks.toMutableList()

        while (buffer.isNotEmpty()) {
            val block = buffer.removeFirstOrNull() ?: break

            if (block.isFile) {
                compressedBlocks.add(block)
                continue
            }

            if (block.length == 0) {
                continue
            }

            val lastBlock = buffer.removeLastUntil { it.isFile } ?: break

            if (block.length > lastBlock.length) {
                // Add the block in place of the free space and prepend the rest of the free space to the buffer
                compressedBlocks.add(Block(lastBlock.fileId, block.index, lastBlock.length))
                buffer.add(0, Block(-1, block.index + lastBlock.length, block.length - lastBlock.length))
            } else if (block.length == lastBlock.length) {
                // Just add the block
                compressedBlocks.add(Block(lastBlock.fileId, block.index, lastBlock.length))
            } else {
                // Add part of block in place of the free space and append the rest of the block to the buffer
                compressedBlocks.add(Block(lastBlock.fileId, block.index, block.length))
                buffer.add(Block(lastBlock.fileId, lastBlock.index, lastBlock.length - block.length))
            }
        }

        return compressedBlocks.sumOf { it.checksum }
    }

    fun part2(): Long {
        val diskMap = loadInput()
            .map { it.digitToInt() }

        var index = 0
        val blocks = diskMap
            .mapIndexed { fileIndex, length ->
                val fileId = if (fileIndex % 2 == 0) {
                    fileIndex / 2
                } else {
                    -1
                }

                Block(fileId, index, length)
                    .also { index += length }
            }
            .filter { it.length > 0 }

        val files = blocks.filter { it.isFile }
        val freeSpace = blocks.filter { !it.isFile }.toMutableList()

        val compressedBlocks: MutableList<Block> = mutableListOf()

        for (file in files.reversed()) {
            val fittingSpace = freeSpace.find { it.length >= file.length && it.index < file.index }

            if (fittingSpace == null) {
                compressedBlocks.add(file)
                continue
            }

            val newFile = Block(file.fileId, fittingSpace.index, file.length)
            compressedBlocks.add(newFile)

            val newFreeSpace = Block(-1, newFile.index + newFile.length, fittingSpace.length - newFile.length)

            freeSpace.remove(fittingSpace)

            if (newFreeSpace.length > 0) {
                freeSpace.add(0, newFreeSpace)
                freeSpace.sortBy { it.index }
            }
        }

        return compressedBlocks.sumOf { it.checksum }
    }
}
