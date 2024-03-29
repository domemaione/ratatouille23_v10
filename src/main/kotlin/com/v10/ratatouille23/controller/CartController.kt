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

    @PostMapping("add/table/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun add(@PathVariable("id") tableId: Long, @RequestBody cartRequestDto: CartRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(this.cartMapper.toDomain(cartService.add(tableId,cartRequestDto))))

    @GetMapping("bill/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun getAllBill() =
        ResponseEntity.ok(ServerResponse.ok(cartService.getAllBill()))


    @GetMapping("bill/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun getBill(@PathVariable("id") cartId: Long) =
        ResponseEntity.ok(ServerResponse.ok(cartService.getBill(cartId)))


    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun getAll() =
        ResponseEntity.ok(ServerResponse.ok(cartService.getAll()))


    @PutMapping("close/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun closeCart(@PathVariable("id") id: Long) =
        ResponseEntity.ok(ServerResponse.ok(this.cartMapper.toDomain(cartService.closeCart(id))))

    //TODO: cancellare un ordine tramite cartId

}