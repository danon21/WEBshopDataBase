package com.danon.webshopdatabase.rest

import com.danon.webshopdatabase.model.CGood
import com.danon.webshopdatabase.model.COrder
import com.danon.webshopdatabase.repositories.IRepositoryOrders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/orders")
class CControllerOrders {

    @Autowired
    private lateinit var repositoryOrders: IRepositoryOrders
    // Получение всех заказов из БД
    @GetMapping("")
    fun getAll() : List<COrder>{
        return repositoryOrders.findAll()
    }
    // Получение заказа по ID
    @GetMapping(params = ["id"])
    fun getById(@RequestParam id: UUID): COrder? {
        return repositoryOrders.findByIdOrNull(id)
    }
    // Создание или обновление заказа
    @PostMapping
    fun save(@RequestBody order : COrder){
        if (order.id == null) {
            order.id = UUID.randomUUID()
        }
        repositoryOrders.save(order)
    }

    // Удаление заказа
    @DeleteMapping
    fun deleteOrder(@RequestBody order : COrder){
        repositoryOrders.delete(order)
    }
}