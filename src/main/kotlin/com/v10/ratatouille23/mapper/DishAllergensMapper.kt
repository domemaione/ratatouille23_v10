package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.AllergensDto
import com.v10.ratatouille23.dto.DishAllergensDto
import com.v10.ratatouille23.model.Allergens
import com.v10.ratatouille23.model.DishAllergens
import org.springframework.stereotype.Component

@Component
class DishAllergensMapper(
): Mapper<DishAllergens, DishAllergensDto> {
    override fun toDomain(e: DishAllergens) =
        DishAllergensDto(
            id = e.id,
            dishId = e.dishId,
            allergenId = e.allergenId
        )

    override fun toEntity(d: DishAllergensDto) =
        DishAllergens(
            id = d.id,
            dishId = d.dishId,
            allergenId = d.allergenId
        )
}