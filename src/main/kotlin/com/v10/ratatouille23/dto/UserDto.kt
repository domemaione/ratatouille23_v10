package com.v10.ratatouille23.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.v10.ratatouille23.utils.UserRoles

data class UserDto (
    val id: Long?,
    val name: String,
    val surname: String,
    val email: String,

    @get:JsonIgnore
    val password: String,

    val role: UserRoles,
    val restaurantId: Long?,
    val enabled: Boolean,
    val firstAccess: Boolean
)