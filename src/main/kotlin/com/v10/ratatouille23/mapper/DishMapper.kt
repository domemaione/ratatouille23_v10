package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.DishDto
import com.v10.ratatouille23.model.Dish
import org.springframework.stereotype.Component

@Component
class DishMapper: Mapper<Dish, DishDto> {
    override fun toDomain(e: Dish) =
        DishDto(
            id = e.id,
            name = e.name,
            description = e.description,
            type = e.type,
            menuId = e.menuId
        )

    override fun toEntity(d: DishDto) =
        Dish(
            id = d.id,
            name = d.name,
            description = d.description,
            type = d.type,
            menuId = d.menuId
        )
}