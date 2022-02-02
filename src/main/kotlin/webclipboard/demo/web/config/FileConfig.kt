package webclipboard.demo.web.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import webclipboard.demo.domain.file.FileFactory
import webclipboard.demo.infra.Md5HashGenerator
import webclipboard.demo.infra.UUIDGenerator

@Configuration
class FileConfig {

    @Bean
    fun fileFactory(): FileFactory {
        return FileFactory(
                idGenerator = UUIDGenerator(),
                hashGenerator = Md5HashGenerator(),
                storageLocation = storageLocation)
    }

    val storageLocation
        get() = "E:/storage"
}