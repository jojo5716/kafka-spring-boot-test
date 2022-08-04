package com.example.kafka.demo.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class UserData(val username: String)
@RestController
class UserController {
    @PostMapping("/users/")
    fun updateStatus(@RequestBody userData: UserData): ResponseEntity<HttpStatus> {
        return ResponseEntity(HttpStatus.OK)
    }
}