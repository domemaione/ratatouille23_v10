package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {}