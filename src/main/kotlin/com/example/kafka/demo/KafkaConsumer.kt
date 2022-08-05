package com.example.kafka.demo

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.util.*
import org.apache.kafka.clients.consumer.KafkaConsumer as KafkaConsumerOriginal

@Configuration
class KafkaConsumer {
    private val EMPLOYEE_TOPIC_NAME = "employee"
    private val logger = LogManager.getLogger(javaClass)
    private val consumer = createConsumer()

    private fun createConsumer(): Consumer<String, String> {
        val props = Properties()

        props["bootstrap.servers"] = "localhost:9092"
        props["group.id"] = "person-processor"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java

        return KafkaConsumerOriginal<String, String>(props)
    }

    @Bean
    fun consume() {
        Thread {
            consumeEmployee(EMPLOYEE_TOPIC_NAME)
        }.start()
    }

    fun consumeEmployee(topicName: String) {
        logger.info("Consuming data from Topic $topicName")

        consumer.subscribe(listOf(topicName))
        while (true) {
            val records = consumer.poll(Duration.ofSeconds(1))
            logger.info("Received ${records.count()} records.")

            records.iterator().forEach {
                val employeeJson = it.value()
                println(employeeJson)
            }
        }
    }
}
