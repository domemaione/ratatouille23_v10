package com.v10.ratatouille23.security

import com.v10.ratatouille23.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val id: Long,
    private val user: User,
    private val email: String,
    private val password: String,
    private val roles:  MutableCollection<out GrantedAuthority>,
    private val enabled: Boolean
): UserDetails {
    fun getId() = this.id
    fun getUser() = this.user
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = this.roles
    override fun getPassword() = this.password
    override fun getUsername() = this.email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = this.enabled

    companion object {
        fun build(user: User) =
            CustomUserDetails(
                id = user.id!!,
                user = user,
                email = user.email,
                password = user.password,
                roles = mutableListOf(SimpleGrantedAuthority(user.role.toString())),
                enabled = user.enabled
            )
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as CustomUserDetails
        return user.id == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}