package webclipboard.demo.infra

import com.google.gson.Gson
import org.springframework.stereotype.Component
import webclipboard.demo.domain.file.File
import java.nio.charset.Charset
import java.util.*

@Component
class FilenameCodec {

    fun encode(file: File): String {
        return String(Base64.getEncoder().encode(Gson().toJson(file).encodeToByteArray()),
                Charset.forName("UTF-8"))
    }

    fun decode(text: String): File {
        val json = String(Base64.getDecoder().decode(text), Charset.forName("UTF-8"))
        return Gson().fromJson(json, File::class.java)
    }

}