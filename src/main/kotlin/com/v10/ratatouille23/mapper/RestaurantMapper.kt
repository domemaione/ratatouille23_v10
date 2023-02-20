package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.RestaurantDto
import com.v10.ratatouille23.model.Restaurant
import org.springframework.stereotype.Component

@Component
class RestaurantMapper(
    private val menuMapper: MenuMapper,
    private val userMapper: UserMapper
): Mapper<Restaurant, RestaurantDto> {
    override fun toDomain(e: Restaurant): RestaurantDto {
        return RestaurantDto(
            id = e.id,
            name = e.name,
            address = e.address,
            createdAt = e.createdAt,
            updateAt = e.updateAt
        )
    }

    override fun toEntity(d: RestaurantDto): Restaurant {
        return Restaurant(
            id = d.id,
            name = d.name,
            address = d.address,
            createdAt = d.createdAt,
            updateAt = d.updateAt
        )
    }
}