package webclipboard.demo.domain.file

import com.google.gson.Gson
import org.springframework.web.multipart.MultipartFile
import webclipboard.demo.web.config.FileConfig
import java.nio.charset.Charset
import java.nio.file.Paths
import java.util.*

class FileFactory constructor(
    val idGenerator: IdGenerator,
    val hashGenerator: HashGenerator,
    val storageLocation: String
) {

    fun createTextFile(content: String, creator: String): File {
        return File(
                creator = creator,
                id = idGenerator.generate(),
                type = FileType.Text,
                hash = hashGenerator.generate(content))
    }

    fun fromFilename(filename: String): File? {
        try {
            return decodePath(filename)
        } catch (_: Exception) {
            return null
        }
    }

    fun createFile(multiPartFile: MultipartFile, creator: String): File {
        val file = File(
                creator = creator,
                id = idGenerator.generate(),
                type = FileType.File,
                hash = null)
        try {
            val destPathWithoutHash = Paths.get(storageLocation, encodePath(file))
            multiPartFile.transferTo(destPathWithoutHash)

            file.hash = hashGenerator.generateFromFile(destPathWithoutHash)
            java.io.File(destPathWithoutHash.toUri()).renameTo(
                    java.io.File(Paths.get(storageLocation, encodePath(file)).toUri()))
        } catch (_: Exception) {

        }
        return file
    }

    private fun encodePath(file: File): String {
        return String(Base64.getEncoder().encode(Gson().toJson(file).encodeToByteArray()),
                Charset.forName("UTF-8"))
    }

    private fun decodePath(str: String): File {
        val json = String(Base64.getDecoder().decode(str), Charset.forName("UTF-8"))
        return Gson().fromJson(json, File::class.java)
    }
}