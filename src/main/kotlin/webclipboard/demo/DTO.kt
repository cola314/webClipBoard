package webclipboard.demo

import java.io.File

data class FileDTO(
    val id: String,
    val file: File,
    val fileCreator: String,
    val text: String
)