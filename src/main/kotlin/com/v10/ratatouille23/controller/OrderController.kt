package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.OrderDto
import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.mapper.DishMapper
import com.v10.ratatouille23.mapper.MenuMapper
import com.v10.ratatouille23.mapper.OrderMapper
import com.v10.ratatouille23.service.MenuService
import com.v10.ratatouille23.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
class OrderController(
    private val orderMapper: OrderMapper,
    private val orderService: OrderService
){
    /*
    @PostMapping("add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun add(@RequestBody orderDto: OrderDto) =
        ResponseEntity.ok(ServerResponse.ok(this.orderMapper.toDomain(orderService.add(orderDto))))*/

}