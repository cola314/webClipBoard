package webclipboard.demo

import org.junit.FixMethodOrder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import webclipboard.demo.domain.file.service.FileService
import webclipboard.demo.web.config.FileConfig

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.runners.MethodSorters
import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Paths

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DemoApplicationTests {

    @Autowired
    private lateinit var fileService: FileService

    @Autowired
    private lateinit var fileConfig: FileConfig

    @BeforeEach
    fun setup() {
        val dir = java.io.File(fileConfig.storageLocation)
        dir.listFiles()
                .toList()
                .forEach {
                    it -> it.delete()
                }
    }

    @Test
    fun add_text_files_then_return_reversed_order() {
        val user = "user"
        val contents = listOf("hello", "world")

        contents.forEach {
            fileService.createText(it, user)
        }

        val result = fileService.getFiles(user)
                .map {
                    fileService.getText(it.id, user)
                }
                .toList()
                .reversed()
        assertEquals(contents, result)
    }

    @Test
    fun deleted_file_is_not_returned() {
        val user = "user"
        val dummy = "dummy"
        val contents = listOf("a", dummy, "b")
        val ids = contents.map {
            fileService.createText(it, user)
        }

        fileService.deleteFile(ids[1], user)

        val result = fileService.getFiles(user)
                .map { fileService.getText(it.id, user) }
                .contains(dummy)
        assertFalse(result)
    }

    @Test
    fun upload_file_and_download_file_then_file_contents_are_same() {
        val user = "user"
        val content = "hello world"
        val fi = Paths.get(fileConfig.storageLocation, "test.txt").toFile()
        fi.createNewFile()
        fi.outputStream().bufferedWriter().use {
            it.write(content)
        }
        val file = MockMultipartFile("test.txt", fi.inputStream())

        val id = fileService.uploadFile(file, user)
        val input = fileService.downloadFile(id, user)

        input.bufferedReader().use {
            assertEquals(content, it.readText())
        }
        assertEquals(1, fileService.getFiles("user").count())
    }
}
