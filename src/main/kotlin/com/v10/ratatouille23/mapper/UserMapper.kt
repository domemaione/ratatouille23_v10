package com.v10.ratatouille23.mapper
import com.v10.ratatouille23.dto.UserDto
import com.v10.ratatouille23.model.User
import org.springframework.data.jpa.domain.AbstractPersistable_.id

import org.springframework.stereotype.Component

@Component
class UserMapper: Mapper<User, UserDto> {
    override fun toDomain(e: User) =
        UserDto(
            id = e.id,
            email = e.email,
            name = e.name,
            surname = e.surname,
            password = e.password,
            role =  e.role,
           restaurantId = e.restaurantId,
            enabled = e.enabled,
            firstAccess = e.firstAccess
        )

    override fun toEntity(d: UserDto) =
       User(
           id = d.id,
           email = d.email,
           name = d.name,
           surname = d.surname,
           password = d.password,
           role = d.role,
           restaurantId = d.restaurantId,
           enabled = d.enabled,
           firstAccess = d.firstAccess
       )
}