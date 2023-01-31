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

            val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")
            val restaurant = this.restaurantService.get(user.restaurantId!!)
            val toSave = Menu(id = null, restaurantId = restaurant.id!!)
            val menuSaved = this.menuRepository.save(toSave)
            restaurant.menuId = menuSaved.id
            this.restaurantRepository.save(restaurant)

        return menuSaved
    }


    fun get(): Menu {
        val restaurantId =
            AuthenticatedUserHelper.get()?.restaurantId ?: throw IllegalStateException("Restaurant not found")

        return menuRepository.findByRestaurantId(restaurantId) ?: throw IllegalStateException("Menu not found")
    }


    fun delete(): Menu{
        val res = this.get()
        val restaurant = this.restaurantService.get(res.id!!)
        restaurant.menuId = null
        this.restaurantRepository.save(restaurant)
        this.menuRepository.deleteById(res.id!!)

        return res
    }

}