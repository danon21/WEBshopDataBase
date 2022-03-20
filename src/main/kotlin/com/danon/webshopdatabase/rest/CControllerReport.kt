package com.danon.webshopdatabase.rest

import com.danon.webshopdatabase.model.WorkData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream

@RestController
@RequestMapping("/report")
class CControllerReport {
    @Autowired
    private lateinit var workData: WorkData
    @GetMapping(produces =
    arrayOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    //value = ["/docx"]
    )
    fun wordReport(): ByteArray? {
        val file = workData.report()
        val outputStream = ByteArrayOutputStream()
        file.write(outputStream)
        file.close()
        return outputStream.toByteArray()
    }
}
