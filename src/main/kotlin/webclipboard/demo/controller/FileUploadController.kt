package webclipboard.demo.controller

import io.swagger.annotations.ApiParam
import org.apache.juli.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping
import webclipboard.demo.config.StorageConfig
import webclipboard.demo.service.UtilService
import java.io.File
import java.nio.file.Paths
import java.io.FileInputStream
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/api")
class FileUploadController {

    @Autowired
    lateinit var utilService: UtilService

    @Autowired
    lateinit var storageConfig: StorageConfig

    val log = LogFactory.getLog(FileUploadController::class.java)

    @GetMapping("/fileNameList")
    fun getAllFileNames(): ResponseEntity<List<String>> {

        val fileNameList = mutableListOf<String>()

        val path = storageConfig.location

        val folder = File(path)

        for (fileEntry in folder.listFiles()) {
            if (fileEntry.isDirectory) {
                log.warn("there should be no directory in this file")
            } else {
                fileNameList.add(fileEntry.name)
            }
        }

        return ResponseEntity
            .ok()
            .body(fileNameList)
    }

    @GetMapping("/{fileName}")
    fun getFile(
        @PathVariable fileName: String,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        try {
            val saveFilePath = "${storageConfig.location}/${fileName}"
            val file = File(saveFilePath)

            response.setHeader(
                "Content-Disposition",
                "attachment;filename=" + file.name
            )

            val fileInputStream = FileInputStream(saveFilePath) // 파일 읽어오기
            val out = response.outputStream
            var read = 0
            val buffer = ByteArray(1024)

            while (fileInputStream.read(buffer).also { read = it } != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
                out.write(buffer, 0, read)
            }
        } catch (e: java.lang.Exception) {
            throw java.lang.Exception("download error")
        }

        return ResponseEntity.ok().build()
    }

    @PostMapping("/upload")
    fun saveFile(
        @RequestPart("file") @ApiParam(value="File", required=true) file: MultipartFile,
    ): ResponseEntity<String> {

        val originalName= file.originalFilename
        val fileName = originalName?.substring(originalName.lastIndexOf("//")+1)

        val uuid = utilService.getRandomString(10)

        val saveFileName = "${storageConfig.getLocation()}/$uuid$fileName"

        val savePath = Paths.get(saveFileName)

        try {
            file.transferTo(savePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ResponseEntity.ok().body(saveFileName)
    }

    @DeleteMapping("/{fileName}")
    fun deleteFile(
        @PathVariable fileName: String
    ): ResponseEntity<Any> {
        val saveFilePath = "${storageConfig.location}/${fileName}"
        val file = File(saveFilePath)

        file.deleteOnExit()

        return ResponseEntity.ok().build()
    }
}