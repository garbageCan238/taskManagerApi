package com.github.ryamal.taskmanagerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan("com.github.ryamal.taskmanagerapi.model")
class TaskManagerApiApplication

fun main(args: Array<String>) {
	runApplication<TaskManagerApiApplication>(*args)
}
