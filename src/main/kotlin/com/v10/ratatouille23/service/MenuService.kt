package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.model.Restaurant
import com.v10.ratatouille23.repository.MenuRepository
import com.v10.ratatouille23.repository.RestaurantRepository
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val restaurantService: RestaurantService,
    private val restaurantRepository: RestaurantRepository
){

    fun add(): Menu {
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")

        val restaurant = this.restaurantService.get(user.restaurantId) ?: throw IllegalStateException("Restaurant not found")

        val toSave = Menu(id = null, restaurantId = restaurant.id!!)
        val menuSaved = this.menuRepository.save(toSave)
        restaurant.menuId = menuSaved.id
        this.restaurantRepository.save(restaurant)
        return menuSaved
    }

}