package com.v10.ratatouille23.mapper

interface Mapper<Entity, Domain> {
    fun toDomain(e: Entity): Domain
    fun toEntity(d: Domain): Entity
    fun toDomains(listEntities: List<Entity>): List<Domain> = listEntities.map { toDomain(it) }
    fun toEntities(listDomain: List<Domain>): List<Entity> = listDomain.map { toEntity(it) }
}