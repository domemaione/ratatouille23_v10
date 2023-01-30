package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.RestaurantDto
import com.v10.ratatouille23.model.Restaurant
import com.v10.ratatouille23.model.User
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
    private val userRepository: UserRepository
) {

    private val logger = Logger.getLogger(RestaurantService::class.java.name)

    fun add(restaurantDto: RestaurantDto): Restaurant {
        this.logger.info("add() - incoming request with obj: $restaurantDto")
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")
        val toSave = Restaurant(
            id = null,
            name = restaurantDto.name,
            address = restaurantDto.address,
            menuId = null,
            userId = user.id,
            createdAt = LocalDateTime.now(),
            updateAt = LocalDateTime.now()
        )

        val saved: Restaurant = this.restaurantRepository.save(toSave) //TODO: prassi spaventosa.... da rivedere
        user.restaurantId = toSave.id
        this.userRepository.save(user)
        this.logger.info("add() - returned obj: $saved")
        return saved
    }

    fun get(id: Long?): Restaurant? {
        return restaurantRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NO_CONTENT, "Restaurant not found!")
    }



    //Restituisce la lista di utenti attivi e con lo stesso ruolo
    fun getUsersByRole(role: UserRoles, enabled: Boolean): List<User> {
        return this.userRepository.findByRoleAndEnabled(role,true)
    }




}