package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.mapper.DishMapper
import com.v10.ratatouille23.mapper.UserMapper
import com.v10.ratatouille23.service.DishService
import com.v10.ratatouille23.service.MenuService
import com.v10.ratatouille23.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dish")
class DishController (
    private val dishMapper: DishMapper,
    private val dishService: DishService
){

    @PostMapping("add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun add(@RequestBody dishRequestDto: DishRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(this.dishMapper.toDomain(dishService.add(dishRequestDto))))


    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun get(@PathVariable("id") id: Long) =
        ResponseEntity.ok(ServerResponse.ok(this.dishMapper.toDomain(dishService.get(id))))


    @PutMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun update(@RequestBody dishRequestDto: DishRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(this.dishMapper.toDomain(dishService.update(dishRequestDto))))


    @PutMapping("category")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun addCategoryToDish(@RequestBody dishRequestDto: DishRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(this.dishMapper.toDomain(dishService.addCategoryToDish(dishRequestDto))))


}
