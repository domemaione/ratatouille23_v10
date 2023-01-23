package com.v10.ratatouille23.controller

import com.v10.ratatouille23.mapper.RestaurantMapper
import com.v10.ratatouille23.service.RestaurantService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.ServerResponse

@RestController
@RequestMapping("/api/restaurant")
class RestaurantController (
    private val restaurantService: RestaurantService,
    private val restaurantMapper: RestaurantMapper
){

    @GetMapping("/users")
    fun getUsers() =
        ResponseEntity.ok(ServerResponse.ok())


}