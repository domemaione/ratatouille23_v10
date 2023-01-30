package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.Restaurant
import com.v10.ratatouille23.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository: CrudRepository<Restaurant, Long> {
    fun findById(restauranId: Long?): Restaurant?
}