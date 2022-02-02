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

}
