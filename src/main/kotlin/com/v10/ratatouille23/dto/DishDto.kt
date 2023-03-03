package com.v10.ratatouille23.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class DishDto (
    val id: Long?,
    val name: String,
    val description: String,
    val nameLan: String?,
    val descriptionLan: String?,
    val cost: Double,
    val menuId: Long,
    val categoryId: Long?
)