package com.danon.webshopdatabase.rest

import com.danon.webshopdatabase.model.CGood
import com.danon.webshopdatabase.repositories.IRepositoryGoods
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/goods")
class CControllerGoods {

    @Autowired
    private lateinit var repositoryGoods: IRepositoryGoods

    //Получение всех товаров из БД
    @GetMapping("")
    fun getAll() : List<CGood>{
        return repositoryGoods.findAll()
    }
    // Получение товара по ID
    @GetMapping(params = ["id"])
    fun getById(@RequestParam id: UUID): CGood? {
        return repositoryGoods.findByIdOrNull(id)
    }

    // Создание или обновление товара
    @PostMapping
    fun save(@RequestBody good : CGood){
        if (good.id == null) {
            good.setGood_id(UUID.randomUUID())
        }
        repositoryGoods.save(good)
    }

    // Удаление товара
    @DeleteMapping
    fun deleteGood(@RequestBody good : CGood){
        repositoryGoods.delete(good)
    }
}