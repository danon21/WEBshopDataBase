package com.danon.webshopdatabase.rest

import com.danon.webshopdatabase.model.WorkData
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
@RestController
@RequestMapping("/upload")
class CControllerFileUploadData {
    @Autowired
    private lateinit var workData: WorkData

    @PostMapping
    fun uploadData(@RequestParam("file") file: MultipartFile){
        val inputStream = ByteArrayInputStream(file.bytes)
        val excel = XSSFWorkbook(inputStream)
        workData.load(excel)
    }

}