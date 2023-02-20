package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.DishDto
import com.v10.ratatouille23.model.Dish
import com.v10.ratatouille23.repository.CategoryRepository
import org.springframework.stereotype.Component

@Component
class DishMapper(): Mapper<Dish, DishDto> {
    override fun toDomain(e: Dish) =
        DishDto(
            id = e.id,
            name = e.name,
            description = e.description,
            cost = e.cost,
            menuId = e.menuId,
            categoryId = e.categoryId
        )

    override fun toEntity(d: DishDto) =
        Dish(
            id = d.id,
            name = d.name,
            description = d.description,
            cost = d.cost,
            menuId = d.menuId,
            categoryId = d.categoryId
        )
}