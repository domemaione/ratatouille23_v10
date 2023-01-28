package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.utils.UserRoles
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun getByEmail(email: String): Optional<User>
    fun findByRoleAndEnabled(role: UserRoles, enabled: Boolean): List<User>
    fun findById(userId: Long?): User?
    fun findByEmail(email: String): Boolean
}