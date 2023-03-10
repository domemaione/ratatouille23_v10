package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.Dish
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DishRepository : JpaRepository<Dish, Long> {
    fun findAllByCategoryId(categoryId: Long): List<Dish>
    fun findAllByMenuId(menuId: Long?): List<Dish>
}