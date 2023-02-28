package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.CartDishDto
import com.v10.ratatouille23.model.CartDish
import org.springframework.stereotype.Component

@Component
class CartDishMapper(
): Mapper<CartDish, CartDishDto> {
    override fun toDomain(e: CartDish) =
        CartDishDto(
            id = e.id,
            cartId = e.cartId,
            dishId = e.dishId,
            userId = e.userId,
            createdAt = e.createdAt,
            updateAt = e.updateAt
        )

    override fun toEntity(d: CartDishDto) =
        CartDish(
            id = d.id,
            cartId = d.cartId,
            dishId = d.dishId,
            userId = d.userId,
            createdAt = d.createdAt,
            updateAt = d.updateAt
        )
}