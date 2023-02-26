package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.CartDto
import com.v10.ratatouille23.model.Cart
import org.springframework.stereotype.Component

@Component
class CartMapper(
): Mapper<Cart, CartDto> {
    override fun toDomain(e: Cart) =
        CartDto(
            id = e.id,
            tableId = e.tableId,
            status = e.status,
            createdAt = e.createdAt,
            updateAt = e.updateAt
        )

    override fun toEntity(d: CartDto) =
        Cart(
            id = d.id,
            tableId = d.tableId,
            status = d.status,
            createdAt = d.createdAt,
            updateAt = d.updateAt
        )
}