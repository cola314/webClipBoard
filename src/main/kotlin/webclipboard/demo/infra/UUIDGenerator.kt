package webclipboard.demo.infra

import webclipboard.demo.domain.file.IdGenerator
import java.util.*

class UUIDGenerator: IdGenerator {

    override fun generate(): String {
        return UUID.randomUUID().toString()
    }

}