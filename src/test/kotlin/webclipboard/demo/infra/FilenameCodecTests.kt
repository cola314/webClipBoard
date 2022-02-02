package webclipboard.demo.infra

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import webclipboard.demo.domain.file.File
import webclipboard.demo.domain.file.FileType

class FilenameCodecTests {

    @Test
    fun decode_encoded_text_then_same_to_original() {
        val file = File("creator", "id", FileType.File, "hash")
        val sut = FilenameCodec()

        val result = sut.decode(sut.encode(file))

        assertEquals(file.creator, result.creator)
        assertEquals(file.id, result.id)
        assertEquals(file.type, result.type)
        assertEquals(file.hash, result.hash)
    }
}