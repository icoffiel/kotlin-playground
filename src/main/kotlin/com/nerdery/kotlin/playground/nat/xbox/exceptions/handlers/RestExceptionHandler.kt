package com.nerdery.kotlin.playground.nat.xbox.exceptions.handlers

import com.nerdery.kotlin.playground.nat.xbox.exceptions.NotFoundException
import com.nerdery.kotlin.playground.nat.xbox.exceptions.domain.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice(annotations = arrayOf(RestController::class))
class RestExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundExpection(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.NOT_FOUND
        return ResponseEntity(
                ErrorResponse(
                        status = httpStatus.value().toString(),
                        title = "Resource Not Found",
                        detail = "The ${ex.kClass} resource was not found with an Id of ${ex.id}. Please ensure that you have supplied the correct ID"),
                httpStatus)
    }
}