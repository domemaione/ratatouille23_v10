package com.v10.ratatouille23.service

import com.v10.ratatouille23.model.Cart
import com.v10.ratatouille23.model.CartDish
import com.v10.ratatouille23.repository.CartDishRepository
import org.springframework.stereotype.Service

@Service
class CartDishService(
    private val cartDishRepository: CartDishRepository
){
    fun getAll(): List<CartDish>{
        return cartDishRepository.findAll()
    }

    fun get(userId: Long): List<CartDish>{
        return cartDishRepository.findByUserId(userId)
    }

}