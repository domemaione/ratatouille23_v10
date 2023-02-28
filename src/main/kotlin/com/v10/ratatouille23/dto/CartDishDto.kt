package com.v10.ratatouille23.dto

import java.time.LocalDateTime

data class CartDishDto (
    val id: Long?,
    val cartId: Long,
    val dishId: Long,
    val userId: Long,
    val createdAt: LocalDateTime?,
    val updateAt: LocalDateTime?
)