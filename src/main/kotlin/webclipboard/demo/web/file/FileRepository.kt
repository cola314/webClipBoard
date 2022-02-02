package webclipboard.demo.web.file

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import webclipboard.demo.web.config.FileConfig
import webclipboard.demo.domain.file.File
import webclipboard.demo.domain.file.FileFactory
import webclipboard.demo.infra.FilenameCodec
import java.io.InputStream
import javax.annotation.PostConstruct

@Repository
class FileRepository
{

    @Autowired
    private lateinit var fileFactory: FileFactory

    @Autowired
    private lateinit var fileConfig: FileConfig

    @Autowired
    private lateinit var filenameCodec: FilenameCodec

    @PostConstruct
    private fun loadFiles(): List<File> {
        val dir = java.io.File(fileConfig.storageLocation)

        if (!dir.exists()) {
            dir.mkdir()
        }

        return dir.listFiles()
                .toMutableList()
                .filter { it.isFile }
                .sortedByDescending { it.lastModified() }
                .mapNotNull { fileFactory.fromFilename(it.name) }
    }

    private fun getFilePath(file: File): String {
        return fileConfig.storageLocation + java.io.File.separatorChar + filenameCodec.encode(file)
    }

    fun getFilesByCreator(creator: String): List<File> {
        return loadFiles()
                .filter { it.isCreator(creator) }
    }

    fun delete(file: File) {
        val fi = java.io.File(getFilePath(file))
        fi.delete()
    }

    fun getFileById(id: String): File? {
        return loadFiles()
                .firstOrNull { it.id == id }
    }

    fun getText(file: File): String {
        val fi = java.io.File(getFilePath(file))
        return fi.inputStream().bufferedReader().readText()
    }

    fun getFileStream(file: File): InputStream {
        val fi = java.io.File(getFilePath(file))
        return fi.inputStream()
    }

    fun createText(content: String, creator: String): File {
        val file = fileFactory.createTextFile(content, creator)
        val fi = java.io.File(getFilePath(file))
        fi.createNewFile()
        fi.writeText(content)
        return file
    }
}