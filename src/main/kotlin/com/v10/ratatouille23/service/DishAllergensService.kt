package com.v10.ratatouille23.service

import com.v10.ratatouille23.model.Dish
import com.v10.ratatouille23.model.DishAllergens
import com.v10.ratatouille23.repository.DishAllergensRepository
import org.springframework.stereotype.Service

@Service
class DishAllergensService(
    private val dishAllergensRepository: DishAllergensRepository

){
    fun getAll(): List<DishAllergens> {
        return dishAllergensRepository.findAll()
    }
}