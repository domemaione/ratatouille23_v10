package com.v10.ratatouille23.dto

import java.time.LocalDateTime

data class CartDishDto (
    var id: Long?,
    var orderId: Long,
    var dishId: Long,
    var userId: Long,
    val createdAt: LocalDateTime?,
    val updateAt: LocalDateTime?
)