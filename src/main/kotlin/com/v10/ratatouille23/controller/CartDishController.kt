package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.dto.request.CartRequestDto
import com.v10.ratatouille23.mapper.CartDishMapper
import com.v10.ratatouille23.mapper.CartMapper
import com.v10.ratatouille23.service.CartDishService
import com.v10.ratatouille23.service.CartService
import com.v10.ratatouille23.service.DishService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cartdish")
class CartDishController(
    private val cartDishMapper: CartDishMapper,
    private val cartDishService: CartDishService
) {

    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun getAll() =
        ResponseEntity.ok(ServerResponse.ok(cartDishService.getAll()))

    @GetMapping("user/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun get(@PathVariable("id") userId: Long) =
        ResponseEntity.ok(ServerResponse.ok(cartDishService.get(userId)))

}