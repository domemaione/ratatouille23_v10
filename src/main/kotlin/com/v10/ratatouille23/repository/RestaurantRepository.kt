package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.Restaurant
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository: CrudRepository<Restaurant, Long> {}