package com.danon.webshopdatabase.repositories

import com.danon.webshopdatabase.model.CUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IRepositoryUsers : JpaRepository<CUser, UUID>{
}