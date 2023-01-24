package com.v10.ratatouille23.dto.request

import com.v10.ratatouille23.utils.UserRoles

data class SignupRequestDto(
    val email: String,
    val name: String,
    val surname: String,
    val password: String,
    val role: UserRoles?,
    )