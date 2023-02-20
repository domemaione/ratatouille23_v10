package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.Allergens
import com.v10.ratatouille23.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface AllergensRepository: JpaRepository<Allergens, Long> {}