package com.scrumdapp.realtimeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RealtimeServiceApplication

fun main(args: Array<String>) {
    runApplication<RealtimeServiceApplication>(*args)
}