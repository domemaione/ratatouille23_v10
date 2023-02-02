package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MenuRepository : JpaRepository<Menu, Long> {
   fun findByRestaurantId(restaurantId: Long): Menu
   fun deleteByRestaurantId(restaurantId: Long): Menu
}