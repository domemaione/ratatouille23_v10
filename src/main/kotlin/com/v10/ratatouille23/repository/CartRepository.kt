package com.v10.ratatouille23.repository


import com.v10.ratatouille23.model.Cart
import com.v10.ratatouille23.utils.CartStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.tableId = :tableId AND c.status = :status")
    fun findByTableIdAndStatus(tableId: Long, status: CartStatus): Cart?
}