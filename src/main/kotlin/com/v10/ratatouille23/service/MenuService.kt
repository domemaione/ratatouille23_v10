package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.model.Menu
import com.v10.ratatouille23.repository.MenuRepository
import com.v10.ratatouille23.repository.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val restaurantService: RestaurantService,
    private val restaurantRepository: RestaurantRepository
){

    fun add(): Menu {

        val foundRestaurantId = AuthenticatedUserHelper.get()?.restaurantId ?: throw IllegalStateException("Restaurant not found")
        val toSave = Menu(id = null, restaurantId = foundRestaurantId)
        val menuSaved = this.menuRepository.save(toSave)
        val restaurant = this.restaurantRepository.findById(foundRestaurantId).get()
        restaurant.menuId = menuSaved.id
        this.restaurantRepository.save(restaurant)
        return menuSaved
    }

    fun delete(): Menu{
        val foundRestaurantId = AuthenticatedUserHelper.get()?.restaurantId ?: throw IllegalStateException("Restaurant not found")
        val foundRestaurant = this.restaurantRepository.findById(foundRestaurantId).get()
        foundRestaurant.menuId = null
        this.restaurantRepository.save(foundRestaurant)
        val foundMenu = this.menuRepository.findByRestaurantId(foundRestaurantId)
        this.menuRepository.deleteById(foundMenu.id!!)

        return foundMenu
    }

}