package com.github.ryamal.taskmanagerapi.repository

import com.github.ryamal.taskmanagerapi.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Date

interface TaskRepository : JpaRepository<Task, Long> {
    fun findByCreationDateBetweenAndCompleted(startDate: Date, endDate: Date, isCompleted: Boolean): List<Task>
    fun findByCreationDateBetween(startDate: Date, endDate: Date) : List<Task>
}