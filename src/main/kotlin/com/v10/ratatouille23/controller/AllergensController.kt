package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.AllergensDto
import com.v10.ratatouille23.dto.DishAllergensDto
import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.dto.request.DishRequestDto
import com.v10.ratatouille23.mapper.AllergensMapper
import com.v10.ratatouille23.mapper.DishMapper
import com.v10.ratatouille23.service.AllergensService
import com.v10.ratatouille23.service.DishService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/allergens")
class AllergensController (
    private val allergensMapper: AllergensMapper,
    private val allergensService: AllergensService
){
    @PostMapping("add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun add(@RequestBody allergensDto: AllergensDto) =
        ResponseEntity.ok(ServerResponse.ok(this.allergensMapper.toDomain(allergensService.add(allergensDto))))

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun get(@PathVariable("id") id: Long) =
        ResponseEntity.ok(ServerResponse.ok(allergensService.get(id)))

    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun getAll() =
        ResponseEntity.ok(ServerResponse.ok(allergensService.getAll()))
    //elimina allergeno
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'WAITER')")
    fun delete(@PathVariable("id") id: Long) =
        ResponseEntity.ok(ServerResponse.ok(allergensService.delete(id)))

}