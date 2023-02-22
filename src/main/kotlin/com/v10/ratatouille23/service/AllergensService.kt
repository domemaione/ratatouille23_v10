package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.AllergensDto
import com.v10.ratatouille23.model.Allergens
import com.v10.ratatouille23.repository.AllergensRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping

@Service
class AllergensService(
    private val allergensRepository: AllergensRepository
) {
    fun add(allergensDto: AllergensDto): Allergens{
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not valid")
        val toSave = Allergens(id = null, name = allergensDto.name)
        val allergenSaved = this.allergensRepository.save(toSave)

        return allergenSaved
    }


    fun getAll(): List<Allergens> {
        return allergensRepository.findAll()
    }


}