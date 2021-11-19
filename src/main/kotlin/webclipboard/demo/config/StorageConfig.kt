package webclipboard.demo.config

import org.springframework.context.annotation.Configuration
import java.nio.file.Path
import java.nio.file.Paths

@Configuration
class StorageConfig {

    var location = "${System.getProperty("user.dir")}/src/main/kotlin/webclipboard/demo/storage"

    fun getLocation(): Path {
        return Paths.get(location)
    }
}