package webclipboard.demo.domain.file

enum class FileType {
    Text,
    File;

    override fun toString(): String {
        return name.toLowerCase()
    }
}