package webclipboard.demo.web.file

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import webclipboard.demo.domain.file.exception.FileNotFoundException
import webclipboard.demo.domain.file.exception.UnauthorizedException

@RestControllerAdvice
class FileControllerAdvice {

    @ExceptionHandler(value = [FileNotFoundException::class])
    fun handleFileNotFoundException(ex: FileNotFoundException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun handleFileNotFoundException(ex: UnauthorizedException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
    }

}