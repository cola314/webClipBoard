package webclipboard.demo.domain.file

class File constructor(
    val creator: String,
    val id: String,
    val type: FileType,
    var hash: String?
) {
    fun isCreator(creator: String): Boolean {
        return this.creator == creator
    }
}