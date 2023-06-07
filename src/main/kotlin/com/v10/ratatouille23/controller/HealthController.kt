package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.ServerResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthController {

    @GetMapping("/check")
    fun get() =
        ResponseEntity.ok("Tutto ok")
}