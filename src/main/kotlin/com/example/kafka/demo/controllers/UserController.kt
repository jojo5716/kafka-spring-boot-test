package com.example.kafka.demo.controllers

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

data class UserData(val username: String)

@RestController
class UserController {
    private fun createProducer(): Producer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = "localhost:9092"
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = StringSerializer::class.java
        return KafkaProducer<String, String>(props)
    }
    @PostMapping("/users/")
    fun updateStatus(@RequestBody userData: UserData): ResponseEntity<HttpStatus> {
        val producer = createProducer()
        val future = producer.send(ProducerRecord("Topic1", "1", "Hello world"))
//        future.get()


        return ResponseEntity(HttpStatus.OK)
    }
}