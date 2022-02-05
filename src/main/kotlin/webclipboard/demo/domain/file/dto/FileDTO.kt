package webclipboard.demo.domain.file.dto

import webclipboard.demo.domain.file.File

data class FileDTO(
    val id: String,
    val type: String,
    val creator: String,
    val hash: String?
)

fun File.toDto(): FileDTO = FileDTO(id, type.toString(), creator, hash)