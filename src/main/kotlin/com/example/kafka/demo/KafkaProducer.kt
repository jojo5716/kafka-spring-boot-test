package com.example.kafka.demo

import com.example.kafka.demo.dtos.EmployeeDto
import com.google.gson.Gson
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.util.*

@Service
class KafkaProducer {
    private val logger = LogManager.getLogger(javaClass)
    private val producer = createProducer()

    private fun createProducer(): Producer<String, String> {
        val props = Properties()

        props["bootstrap.servers"] = "localhost:9092"
        props["key.serializer"] = StringSerializer::class.java.canonicalName
        props["value.serializer"] = StringSerializer::class.java.canonicalName

        return KafkaProducer<String, String>(props)
    }

    fun produce(topicName: String, employeeDto: EmployeeDto) {
        logger.info("Sending data to Topic $topicName with data $employeeDto")

        val futureResult = producer.send(ProducerRecord(topicName, employeeDto.id, Gson().toJson(employeeDto)))

        // Wait for to write acknowledgment
        futureResult.get()
    }
}
