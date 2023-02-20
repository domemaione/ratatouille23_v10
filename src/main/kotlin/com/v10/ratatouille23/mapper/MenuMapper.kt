package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.model.Menu
import org.springframework.stereotype.Component

@Component
class MenuMapper(
): Mapper<Menu, MenuDto> {
    override fun toDomain(e: Menu) =
        MenuDto(
            id = e.id,
            restaurantId = e.restaurantId
        )

    override fun toEntity(d: MenuDto) =
        Menu(
            id = d.id,
            restaurantId = d.restaurantId
        )
}