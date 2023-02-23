package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.RestaurantDto
import com.v10.ratatouille23.model.Restaurant
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.MenuRepository
import com.v10.ratatouille23.repository.RestaurantRepository
import com.v10.ratatouille23.repository.UserRepository
import com.v10.ratatouille23.utils.UserRoles
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.logging.Logger

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository
) {

    private val logger = Logger.getLogger(RestaurantService::class.java.name)

    fun add(restaurantDto: RestaurantDto): Restaurant {
        this.logger.info("add() - incoming request with obj: $restaurantDto")
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")

        if(user.restaurantId != null)
            throw IllegalStateException("Restaurant already exists")

        val toSave = Restaurant(
            id = null,
            name = restaurantDto.name,
            address = restaurantDto.address,
            createdAt = LocalDateTime.now(),
            updateAt = LocalDateTime.now()
        )

        val saved = this.restaurantRepository.save(toSave)
        user.restaurantId = toSave.id
        this.userRepository.save(user)
        this.logger.info("add() - returned obj: $saved")
        return saved
    }

    fun get(id: Long): Restaurant {
        val found = this.restaurantRepository.findById(id)
        if(found.isEmpty)
            throw IllegalStateException("Restaurant not found")

        return found.get()
    }

    //Restituisce la lista di utenti attivi e con lo stesso ruolo
    fun getUsersByRole(role: UserRoles, enabled: Boolean): List<User> {
        return this.userRepository.findByRoleAndEnabled(role,true)
    }

    fun delete(): Restaurant{
        val restaurantId = AuthenticatedUserHelper.get()?.restaurantId ?: throw ResponseStatusException(HttpStatus.NO_CONTENT)
        val restaurant = this.restaurantRepository.findById(restaurantId) //forse si potrebbe evitare
        if(restaurant.isEmpty)
            throw ResponseStatusException(HttpStatus.NO_CONTENT, "Restaurant not found!")

        this.restaurantRepository.deleteById(restaurantId)
        this.userRepository.deleteUsers()
        return restaurant.get()
    }


}