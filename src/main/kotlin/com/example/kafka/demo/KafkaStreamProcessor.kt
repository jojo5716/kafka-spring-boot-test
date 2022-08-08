package com.example.kafka.demo

import com.example.kafka.demo.dtos.EmployeeDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties

val jsonMapper = ObjectMapper().apply {
    registerKotlinModule()
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    setDateFormat(StdDateFormat())
}

@Configuration
class KafkaStreamProcessor {
    private val EMPLOYEE_TOPIC_NAME = "employee"
    private val EMPLOYEE_STREAM_TOPIC_NAME = "employee_stream"
    private val logger = LogManager.getLogger(javaClass)

    @Bean
    fun processAll() {
        Thread{
            processEmployee()
        }.start()
    }

    fun processEmployee() {
        val streamsBuilder = StreamsBuilder()

        val personJsonStream: KStream<String, String> = streamsBuilder
            .stream(EMPLOYEE_TOPIC_NAME, Consumed.with(Serdes.String(), Serdes.String()))

        val employeeStream: KStream<String, EmployeeDto> = personJsonStream.mapValues { v ->
            val person = jsonMapper.readValue(v, EmployeeDto::class.java)
            logger.debug("Person: $person")
            person
        }

        val resStream: KStream<String, String> = employeeStream.map { _, p ->
            logger.debug("Employee ID: ${p.id}")
            KeyValue(p.id, "${p.salary}")
        }

        resStream.to(EMPLOYEE_STREAM_TOPIC_NAME, Produced.with(Serdes.String(), Serdes.String()))

        val topology = streamsBuilder.build()

        val props = Properties()

        props["bootstrap.servers"] = "localhost:9092"
        props["application.id"] = "margin-service"
        val streams = KafkaStreams(topology, props)
        streams.start()
    }
}
