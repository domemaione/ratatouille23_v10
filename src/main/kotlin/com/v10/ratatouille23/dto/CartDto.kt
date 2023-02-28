package com.v10.ratatouille23.dto

import com.v10.ratatouille23.utils.CartStatus
import java.time.LocalDateTime

data class CartDto (
    val id: Long?,
    val tableId: Long,
    val status: CartStatus,
    val createdAt: LocalDateTime?,
    val updateAt: LocalDateTime?
)