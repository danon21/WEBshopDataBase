package com.danon.webshopdatabase.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/upload")
class CControllerFileUploadData {
//    @Autowired
//    private lateinit var main: Main
//    @PostMapping
//    fun uploadDateFromFile(@RequestParam("file") file: MultipartFile) {
//        val inputStream = ByteArrayInputStream(file.bytes)
//        val excel = XSSFWorkbook(inputStream)
//        if (main.load(excel) = 1) {
//            excel.close()
//            throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Неправильное
//                    представление данных в файле")
//        }
//        excel.close()
//    }

}