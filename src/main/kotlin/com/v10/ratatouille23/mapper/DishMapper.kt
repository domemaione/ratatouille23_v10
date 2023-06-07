package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.DishDto
import com.v10.ratatouille23.model.Dish
import org.springframework.stereotype.Component

@Component
class DishMapper(): Mapper<Dish, DishDto> {
    override fun toDomain(e: Dish) =
        DishDto(
            id = e.id,
            name = e.name,
            description = e.description,
            nameLan = e.nameLan,
            descriptionLan = e.descriptionLan,
            cost = e.cost,
            menuId = e.menuId,
            categoryId = e.categoryId
        )

    override fun toEntity(d: DishDto) =
        Dish(
            id = d.id,
            name = d.name,
            description = d.description,
            nameLan = d.nameLan,
            descriptionLan = d.descriptionLan,
            cost = d.cost,
            menuId = d.menuId,
            categoryId = d.categoryId
        )
}