package com.v10.ratatouille23.component

import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.security.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder

object AuthenticatedUserHelper {
    fun get(): User? {
        val auth = SecurityContextHolder.getContext().authentication ?: return null
        return try {
            val user = auth.principal as CustomUserDetails
            user.getUser()
        } catch (e: Exception) {
            null
        }
    }

}