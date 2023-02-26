package com.v10.ratatouille23.dto

import com.v10.ratatouille23.utils.OrderStatus
import java.time.LocalDateTime

data class OrderDto (
    var id: Long?,
    var tableId: Long,
    var status: OrderStatus,
    val createdAt: LocalDateTime?
)