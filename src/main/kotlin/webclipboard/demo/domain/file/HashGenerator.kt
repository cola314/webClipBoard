package webclipboard.demo.domain.file

import java.nio.file.Path

interface HashGenerator {
    fun generate(content: String): String
    fun generateFromFile(path: Path): String
}