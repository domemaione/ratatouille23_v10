package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.model.Menu
import org.springframework.stereotype.Component

@Component
class MenuMapper(
    private val dishMapper: DishMapper
): Mapper<Menu, MenuDto> {
    override fun toDomain(e: Menu) =
        MenuDto(
            id = e.id,
            restaurantId = e.restaurantId
           // dishes = e.dishes?.let { (this.dishMapper.toDomains(it)).toMutableList() }
        )

    override fun toEntity(d: MenuDto) =
        Menu(
            id = d.id,
            restaurantId = d.restaurantId
            //dishes = d.dishes?.let { (this.dishMapper.toEntities(it)).toMutableList() },
        )
}