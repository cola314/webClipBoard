package webclipboard.demo.domain.file.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import webclipboard.demo.domain.file.File
import webclipboard.demo.domain.file.FileFactory
import webclipboard.demo.domain.file.dto.FileDTO
import webclipboard.demo.domain.file.dto.toDto
import webclipboard.demo.domain.file.exception.FileNotFoundException
import webclipboard.demo.domain.file.exception.UnauthorizedException
import webclipboard.demo.web.file.FileRepository
import java.io.InputStream

@Service
class FileService constructor(
    val fileFactory: FileFactory,
    val fileRepository: FileRepository
) {

    private fun getFileByIdOrThrowIfCreatorOwnedFileNotExists (id: String, creator: String): File {
        val file = fileRepository.getFileById(id) ?: throw FileNotFoundException("파일이 존재하지 않습니다 id: $id")

        if (!file.isCreator(creator))
            throw UnauthorizedException("권한이 없습니다")

        return file
    }

    fun getFiles(creator: String): List<FileDTO> {
        val files = fileRepository.getFilesByCreator(creator)
        return files.map { it.toDto() }
    }

    fun deleteFile(id: String, creator: String) {
        val file = getFileByIdOrThrowIfCreatorOwnedFileNotExists(id, creator)
        fileRepository.delete(file)
    }

    fun getText(id: String, creator: String): String {
        val file = getFileByIdOrThrowIfCreatorOwnedFileNotExists(id, creator)
        return fileRepository.getText(file)
    }

    fun createText(content: String, creator: String): String {
        val file = fileRepository.createText(content, creator)
        return file.id
    }

    fun downloadFile(id: String, creator: String): InputStream {
        val file = getFileByIdOrThrowIfCreatorOwnedFileNotExists(id, creator)
        return fileRepository.getFileStream(file)
    }

    fun uploadFile(multiPartFile: MultipartFile, creator: String): String {
        val file = fileFactory.createFile(multiPartFile, creator)
        return file.id
    }

}