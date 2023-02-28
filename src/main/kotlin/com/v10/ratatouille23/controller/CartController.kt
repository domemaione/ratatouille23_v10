package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.dto.request.CartRequestDto
import com.v10.ratatouille23.mapper.CartMapper
import com.v10.ratatouille23.service.CartService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartMapper: CartMapper,
    private val cartService: CartService
){

    @PostMapping("add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun add(@PathVariable("id") id: Long, @RequestBody cartRequestDto: CartRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(this.cartMapper.toDomain(cartService.add(id,cartRequestDto))))

    @PutMapping("close/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun closeCart(@PathVariable("id") id: Long) =
        ResponseEntity.ok(ServerResponse.ok(this.cartMapper.toDomain(cartService.closeCart(id))))


}