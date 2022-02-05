package webclipboard.demo.web.file

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import webclipboard.demo.domain.file.service.FileService
import org.springframework.util.StreamUtils
import webclipboard.demo.domain.file.dto.FileDTO
import javax.servlet.http.HttpServletResponse


@RestController
class FileController {

    @Autowired
    private lateinit var fileService: FileService

    @GetMapping("/api/files/{creator}")
    fun getFiles(
        @PathVariable creator: String
    ): List<FileDTO> {
        return fileService.getFiles(creator)
    }

    @DeleteMapping("/api/file/{id}/{creator}")
    fun deleteFile(
        @PathVariable id: String,
        @PathVariable creator: String
    ) {
        fileService.deleteFile(id, creator)
    }

    @GetMapping("/api/text/{id}/{creator}")
    fun getText(
        @PathVariable id: String,
        @PathVariable creator: String
    ): String {
        return fileService.getText(id, creator)
    }

    @PostMapping("/api/text/{creator}")
    fun createText(
        @PathVariable creator: String,
        @RequestBody content: String
    ): String {
        return fileService.createText(content, creator)
    }

    @GetMapping("/api/file/{id}/{creator}")
    fun downloadFile(
        @PathVariable id: String,
        @PathVariable creator: String,
        response: HttpServletResponse
    ) {
        val inputStream = fileService.downloadFile(id, creator)
        StreamUtils.copy(inputStream, response.outputStream)
        response.flushBuffer()
        inputStream.close()
    }

    @PostMapping("/api/file/{creator}")
    fun uploadFile(
        @PathVariable creator: String,
        multiPartFile: MultipartFile
    ): String {
        return fileService.uploadFile(multiPartFile, creator)
    }
}