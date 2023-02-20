package com.v10.ratatouille23.mapper

import com.v10.ratatouille23.dto.AllergensDto
import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.model.Allergens
import com.v10.ratatouille23.model.Menu
import org.springframework.stereotype.Component
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All

@Component
class AllergensMapper(
): Mapper<Allergens, AllergensDto> {
    override fun toDomain(e: Allergens) =
        AllergensDto(
            id = e.id,
           name = e.name
        )

    override fun toEntity(d: AllergensDto) =
        Allergens(
            id = d.id,
            name = d.name
        )
}