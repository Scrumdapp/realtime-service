package com.scrumdapp.realtimeservice.kafka

import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.kafka.autoconfigure.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.converter.JacksonJsonMessageConverter
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer

@Configuration
class DefaultObjectFactory {

    @Bean
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<String, String> {
        val props = kafkaProperties.buildConsumerProperties()

        return DefaultKafkaConsumerFactory(
            props,
            StringDeserializer(),
            StringDeserializer()
        )
    }
    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, String>
    ): ConcurrentKafkaListenerContainerFactory<String, String> {
        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            setConsumerFactory(consumerFactory)
            setRecordMessageConverter(JacksonJsonMessageConverter())
        }
    }

}