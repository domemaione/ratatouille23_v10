package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
   fun findByName(name: String): Optional<Category>
}