package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.dto.OrderDto
import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.model.Order
import org.springframework.stereotype.Component

@Component
class OrderMapper(
): Mapper<Order, OrderDto> {
    override fun toDomain(e: Order) =
        OrderDto(
            id = e.id,
            tableId = e.tableId
        )

    override fun toEntity(d: OrderDto) =
        Order(
            id = d.id,
            tableId = d.tableId
        )
}