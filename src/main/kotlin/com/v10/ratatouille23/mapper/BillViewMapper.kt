package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.BillViewDto
import com.v10.ratatouille23.model.BillView
import org.springframework.stereotype.Component

@Component
class BillViewMapper(
): Mapper<BillView, BillViewDto> {
    override fun toDomain(e: BillView) =
        BillViewDto(
            id = e.id,
            cartId = e.cartId,
            total = e.total
        )

    override fun toEntity(d: BillViewDto) =
        BillView(
            id = d.id,
            cartId = d.cartId,
            total = d.total
        )
}