package com.v10.ratatouille23.dto.request

data class DishRequestDto (
    val id: Long?,
    val name: String,
    val description: String,
    val cost: Double = 0.0,
    val categoryName: String?,
    val allergens: Array<Long>?
)