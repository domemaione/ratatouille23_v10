package com.v10.ratatouille23.repository

import com.v10.ratatouille23.model.CartDish
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartDishRepository : JpaRepository<CartDish, Long> {}