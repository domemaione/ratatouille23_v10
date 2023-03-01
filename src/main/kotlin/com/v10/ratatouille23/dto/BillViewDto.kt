package com.v10.ratatouille23.dto

import java.math.BigDecimal

data class BillViewDto(
    val id: Long,
    val cartId: Long,
    val total: BigDecimal
)