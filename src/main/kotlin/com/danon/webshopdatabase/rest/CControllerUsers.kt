package com.danon.webshopdatabase.rest

import com.danon.webshopdatabase.model.CUser
import com.danon.webshopdatabase.repositories.IRepositoryUsers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class CControllerUsers {

    @Autowired
    private lateinit var repositoryUsers : IRepositoryUsers
    // Получение всех пользователей из БД
    @GetMapping("")
    fun getAll() : List<CUser>{
        return repositoryUsers.findAll()
    }
    // Получение пользователя по ID
    @GetMapping(params = ["id"])
    fun getById(@RequestParam id: UUID): CUser? {
        return repositoryUsers.findByIdOrNull(id)
    }
    // Создание или обновление пользователя
    @PostMapping
    fun save(@RequestBody user: CUser) {
        if (user.id == null) {
            user.id = UUID.randomUUID()
        }
        repositoryUsers.save(user)
    }

    // Удаление пользователя
    @DeleteMapping
    fun deleteUser(@RequestBody user: CUser){
        repositoryUsers.delete(user)
    }



}