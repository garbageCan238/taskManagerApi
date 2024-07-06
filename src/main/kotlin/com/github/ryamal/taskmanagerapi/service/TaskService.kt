package com.github.ryamal.taskmanagerapi.service

import com.github.ryamal.taskmanagerapi.model.Task
import com.github.ryamal.taskmanagerapi.repository.TaskRepository
import com.github.ryamal.taskmanagerapi.utils.DateUtil
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
        val endOfDay = DateUtil.getEndOfDay()
        val startOfDay = DateUtil.getStartOfDay()
        return taskRepository.findByCreationDateBetweenAndCompleted(startOfDay, endOfDay, completed)
    }

    fun getTodayTasks(): List<Task> {
        val endOfDay = DateUtil.getEndOfDay()
        val startOfDay = DateUtil.getStartOfDay()
        return taskRepository.findByCreationDateBetween(startOfDay, endOfDay)
    }

    fun getCurrentWeekTasks(completed: Boolean): List<Task> {
        val endOfDay = DateUtil.getEndOfDay()
        val startOfWeek = DateUtil.getStartOfWeek()
        return taskRepository.findByCreationDateBetweenAndCompleted(startOfWeek, endOfDay, completed)
    }

    fun getCurrentWeekTasks(): List<Task> {
        val endOfDay = DateUtil.getEndOfDay()
        val startOfWeek = DateUtil.getStartOfWeek()
        return taskRepository.findByCreationDateBetween(startOfWeek, endOfDay)
    }

    fun getCurrentMonthTasks(completed: Boolean): List<Task> {
        val endOfDay = DateUtil.getEndOfDay()
        val startOfMonth = DateUtil.getStartOfMonth()
        return taskRepository.findByCreationDateBetweenAndCompleted(startOfMonth, endOfDay, completed)
    }

    fun getCurrentMonthTasks(): List<Task> {
        val endOfDay = DateUtil.getEndOfDay()
        val startOfMonth = DateUtil.getStartOfMonth()
        return taskRepository.findByCreationDateBetween(startOfMonth, endOfDay)
    }
}