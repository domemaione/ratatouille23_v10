package com.v10.ratatouille23.dto

import com.v10.ratatouille23.utils.DishType

data class DishDto (
    val id: Long?,
    val name: String,
    val description: String,
    val type: DishType,
    val menuId: Long
)