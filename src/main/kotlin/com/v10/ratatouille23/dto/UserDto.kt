package com.v10.ratatouille23.dto

data class UserDto (
    val id: Long?,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String,
    val restaurantId: Long?,
    val enabled: Boolean
)