package com.danon.webshopdatabase.repositories

import com.danon.webshopdatabase.model.CGood
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IRepositoryGoods : JpaRepository<CGood, UUID> {
}