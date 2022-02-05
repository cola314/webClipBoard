package webclipboard.demo.domain.file

import com.google.gson.Gson
import org.springframework.web.multipart.MultipartFile
import webclipboard.demo.infra.FilenameCodec
import webclipboard.demo.web.config.FileConfig
import java.nio.charset.Charset
import java.nio.file.Paths
import java.util.*

class FileFactory constructor(
    private val idGenerator: IdGenerator,
    private val hashGenerator: HashGenerator,
    private val storageLocation: String,
    private val filenameCodec: FilenameCodec
) {

    fun createTextFile(content: String, creator: String): File {
        return File(
                creator = creator,
                id = idGenerator.generate(),
                type = FileType.Text,
                hash = hashGenerator.generate(content))
    }

    fun fromFilename(filename: String): File? {
        return try {
            filenameCodec.decode(filename)
        } catch (_: Exception) {
            null
        }
    }

    fun createFile(multiPartFile: MultipartFile, creator: String): File {
        val file = File(
                creator = creator,
                id = idGenerator.generate(),
                type = FileType.File,
                hash = null)
        try {
            val destPathWithoutHash = Paths.get(storageLocation, filenameCodec.encode(file))
            multiPartFile.transferTo(destPathWithoutHash)

            file.hash = hashGenerator.generateFromFile(destPathWithoutHash)
            java.io.File(destPathWithoutHash.toUri()).renameTo(
                    java.io.File(Paths.get(storageLocation, filenameCodec.encode(file)).toUri()))
        } catch (_: Exception) {

        }
        return file
    }
}