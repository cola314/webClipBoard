package webclipboard.demo.infra

import org.springframework.util.DigestUtils
import webclipboard.demo.domain.file.HashGenerator
import java.nio.file.Files
import java.nio.file.Path


class Md5HashGenerator: HashGenerator {

    override fun generate(content: String): String {
        return DigestUtils.md5DigestAsHex(content.toByteArray(Charsets.UTF_8))
    }

    override fun generateFromFile(path: Path): String {
        return Files.newInputStream(path)
                .use { DigestUtils.md5DigestAsHex(it) }
    }

}