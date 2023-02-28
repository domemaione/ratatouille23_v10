package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.BillView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BillViewRepository: JpaRepository<BillView, Long> {
    @Query("SELECT c.id as cart_id, SUM(d.cost) as total FROM Cart c JOIN CartDish cd ON c.id = cd.cartId JOIN Dish d ON cd.dishId = d.id WHERE c.status = 'CLOSED' GROUP BY c.id")
    fun getAllBill(): List<BillView>
}