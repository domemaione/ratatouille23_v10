package com.v10.ratatouille23.repository


import com.v10.ratatouille23.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    fun findByTableId(tableId: Long): Cart?
}