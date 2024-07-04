package com.github.ryamal.taskmanagerapi.service

import com.github.ryamal.taskmanagerapi.model.Task
import com.github.ryamal.taskmanagerapi.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(private val taskRepository: TaskRepository) {
    fun getAllTasks(): List<Task> = taskRepository.findAll()

    fun getTaskById(id: Long): Optional<Task> = taskRepository.findById(id)

    fun createTask(task: Task): Task = taskRepository.save(task)

    fun updateTask(id: Long, task: Task): Task {
        return if (taskRepository.existsById(id)) {
            taskRepository.save(
                Task(
                    id = id,
                    title = task.title,
                    description = task.description,
                    completed = task.completed,
                    creationDate = task.creationDate
                )
            )
        } else throw RuntimeException("Task $id not found")
    }

    fun deleteTask(id: Long) {
        return if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id)
        } else throw RuntimeException("Task $id not found")
    }

    fun updateTaskCompleteness(id: Long, isCompleted: Boolean): Task {
        val task = taskRepository.findById(id).orElseThrow { RuntimeException("Task $id not found") }
        return taskRepository.save(
            Task(
                id = task.id,
                title = task.title,
                description = task.description,
                completed = isCompleted,
                creationDate = task.creationDate
            )
        )
    }

    fun getTodayTasks(completed: Boolean): List<Task> {
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        val startOfDay = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return taskRepository.findByCreationDateBetweenAndCompleted(startOfDay, endOfDay, completed)
    }

    fun getTodayTasks(): List<Task> {
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        val startOfDay = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return taskRepository.findByCreationDateBetween(startOfDay, endOfDay)
    }

    fun getCurrentWeekTasks(completed: Boolean): List<Task> {
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        val startOfWeek = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return taskRepository.findByCreationDateBetweenAndCompleted(startOfWeek, endOfDay, completed)
    }

    fun getCurrentWeekTasks(): List<Task> {
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        val startOfWeek = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return taskRepository.findByCreationDateBetween(startOfWeek, endOfDay)
    }

    fun getCurrentMonthTasks(completed: Boolean): List<Task> {
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        val startOfMonth = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return taskRepository.findByCreationDateBetweenAndCompleted(startOfMonth, endOfDay, completed)
    }

    fun getCurrentMonthTasks(): List<Task> {
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        val startOfMonth = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return taskRepository.findByCreationDateBetween(startOfMonth, endOfDay)
    }
}