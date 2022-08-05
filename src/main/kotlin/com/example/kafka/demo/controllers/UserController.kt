package com.example.kafka.demo.controllers

import com.example.kafka.demo.KafkaProducer
import com.example.kafka.demo.dtos.EmployeeDto
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val kafkaProducer: KafkaProducer) {
    private val logger = LogManager.getLogger(javaClass)

    private val TOPIC_NAME = "employee"

    @GetMapping("/users/")
    fun updateStatus(): ResponseEntity<HttpStatus> {
        logger.info("Getting employee data ...")

        val employee = EmployeeDto(id = "1", name = "Jhon", lastName = "Doe", salary = 2000.0)
        kafkaProducer.produce(TOPIC_NAME, employee)

        return ResponseEntity(HttpStatus.OK)
    }
}