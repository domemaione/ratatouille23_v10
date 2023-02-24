package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.dto.TableRestaurantDto
import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.model.TableRestaurant
import org.springframework.stereotype.Component

@Component
class TableRestaurantMapper(
): Mapper<TableRestaurant, TableRestaurantDto> {
    override fun toDomain(e: TableRestaurant) =
        TableRestaurantDto(
            id = e.id,
            seats = e.seats,
            restaurantId = e.restaurantId
        )

    override fun toEntity(d: TableRestaurantDto) =
        TableRestaurant(
            id = d.id,
            seats = d.seats,
            restaurantId = d.restaurantId
        )
}