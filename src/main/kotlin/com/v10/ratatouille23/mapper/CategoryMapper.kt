package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.CategoryDto
import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.model.Category
import com.v10.ratatouille23.model.Menu
import org.springframework.stereotype.Component

@Component
class CategoryMapper(): Mapper<Category, CategoryDto> {
    override fun toDomain(e: Category) =
        CategoryDto(
            id = e.id,
            name = e.name,
            priority = e.priority
        )

    override fun toEntity(d: CategoryDto) =
        Category(
            id = d.id,
            name = d.name,
            priority = d.priority
        )
}