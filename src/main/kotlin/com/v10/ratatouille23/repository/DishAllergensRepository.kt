package com.v10.ratatouille23.repository
import com.v10.ratatouille23.model.DishAllergens
import org.springframework.data.jpa.repository.JpaRepository

interface DishAllergensRepository: JpaRepository<DishAllergens, Long> {
}