package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.TableRestaurantDto
import com.v10.ratatouille23.dto.request.SignupRequestDto
import com.v10.ratatouille23.dto.request.TableRequestDto
import com.v10.ratatouille23.model.TableRestaurant
import com.v10.ratatouille23.repository.TableRestaurantRepository
import org.springframework.stereotype.Service

@Service
class TableRestaurantService(
    private val tableRestaurantRepository: TableRestaurantRepository
){
    fun add(tableRequestDto: TableRequestDto): TableRestaurant{
        val foundRestaurantId = AuthenticatedUserHelper.get()?.restaurantId ?: throw IllegalStateException("Restaurant not found")
        val toSave = TableRestaurant(id = null, seats = tableRequestDto.seats, restaurantId = foundRestaurantId)
        val saved = this.tableRestaurantRepository.save(toSave)
        return saved
    }

}