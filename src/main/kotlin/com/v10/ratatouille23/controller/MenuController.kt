package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.DishDto
import com.v10.ratatouille23.dto.MenuDto
import com.v10.ratatouille23.dto.RestaurantDto
import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.mapper.DishMapper
import com.v10.ratatouille23.mapper.MenuMapper
import com.v10.ratatouille23.service.MenuService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/menu")
class MenuController(
    private val menuService: MenuService,
    private val menuMapper: MenuMapper,
    private val dishMapper: DishMapper
){

    @PostMapping("add")
    @PreAuthorize("hasAuthority('ADMIN')") //il menu può essere creato solo dall'admin
    fun add() =
        ResponseEntity.ok(ServerResponse.ok(this.menuMapper.toDomain(menuService.add())))


    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')") //il menu può essere creato solo dall'admin
    fun delete() =
        ResponseEntity.ok(ServerResponse.ok(this.menuMapper.toDomain(menuService.delete())))

}