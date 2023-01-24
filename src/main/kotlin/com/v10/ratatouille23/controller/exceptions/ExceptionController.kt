package com.v10.ratatouille23.controller.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

@ControllerAdvice
class ExceptionController {
    private val logger = Logger.getLogger(ExceptionController::class.java.name)

    @ExceptionHandler
    fun handlerResponseStatusException(e: ResponseStatusException): ResponseEntity<Any> {
        this.logger.severe("handlerResponseStatusException() - response status exception: ${e.message}")
        return ResponseEntity(e.message, e.status)
    }

    @ExceptionHandler
    fun handlerIllegalStateException(e: IllegalStateException): ResponseEntity<Any> {
        this.logger.severe("handlerIllegalStateException() - error: ${e.message}")
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handlerAccessDeniedException(e: AccessDeniedException): ResponseEntity<Any> {
        this.logger.severe("handlerAccessDeniedException() - error: ${e.message}")
        return ResponseEntity("Forbidden", HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler
    fun handlerException(e: Exception): ResponseEntity<Any> {
        this.logger.severe("handlerException() - error: ${e.message}")
        e.printStackTrace()
        return ResponseEntity("Unexpected error during the execution", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}