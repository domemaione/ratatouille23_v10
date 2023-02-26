package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.CategoryDto
import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.dto.request.PriorityRequestDto
import com.v10.ratatouille23.mapper.CategoryMapper
import com.v10.ratatouille23.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/category")
class CategoryController(
    private val categoryService: CategoryService,
    private val categoryMapper: CategoryMapper
){

    @PostMapping("add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun add(@RequestBody categoryDto: CategoryDto) =
        ResponseEntity.ok(ServerResponse.ok(this.categoryMapper.toDomain(categoryService.add(categoryDto))))

    @PostMapping("priority/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun addPriority(@PathVariable("id") id: Long,@RequestBody priorityRequestDto: PriorityRequestDto ) =
        ResponseEntity.ok(ServerResponse.ok(this.categoryMapper.toDomain(categoryService.addPriority(id,priorityRequestDto))))

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun get(@PathVariable("id") id: Long) =
        ResponseEntity.ok(ServerResponse.ok(this.categoryMapper.toDomain(categoryService.get(id))))

    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun getAll() =
        ResponseEntity.ok(ServerResponse.ok(categoryService.getAll()))


    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun delete(@PathVariable("id") id: Long) =
        ResponseEntity.ok(ServerResponse.ok(this.categoryMapper.toDomain(categoryService.delete(id))))

}