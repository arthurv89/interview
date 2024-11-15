package com.swipecrowd.interview.application;

import com.swipecrowd.interview.service.ExpenseTooHighException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ExpenseTooHighException::class)
    fun handleExpenseTooHighException(ex: ExpenseTooHighException): ResponseEntity<String> {
        return ResponseEntity("Error: ${ex.message}", HttpStatus.BAD_REQUEST)
    }
}
