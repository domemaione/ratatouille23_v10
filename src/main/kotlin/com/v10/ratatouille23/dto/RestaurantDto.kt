package com.v10.ratatouille23.dto

import java.sql.Timestamp
//nel file dto, la data class ha come parametri le variabili costanti
data class RestaurantDto (
    val id: Long?,
    val name: String,
    val address: String,
    val menuId: Long?,
    val userId: Long,
    val updateAt: Timestamp?,
    val createdAt: Timestamp?
    )