package com.v10.ratatouille23.dto

import java.time.LocalDateTime

//nel file dto, la data class ha come parametri le variabili costanti
data class RestaurantDto(
    val id: Long?,
    val name: String,
    val address: String,
    val updateAt: LocalDateTime?,
    val createdAt: LocalDateTime?
    )