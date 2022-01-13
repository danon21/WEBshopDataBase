package com.danon.webshopdatabase.repositories

import com.danon.webshopdatabase.model.COrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IRepositoryOrders : JpaRepository<COrder, UUID> {
}