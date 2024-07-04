package com.github.ryamal.taskmanagerapi.controller

import com.github.ryamal.taskmanagerapi.model.Task
import com.github.ryamal.taskmanagerapi.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/tasks")
class TaskController @Autowired constructor(private val taskService: TaskService) {
    @GetMapping
    fun getAllTasks(): List<Task> = taskService.getAllTasks()

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): Task? = taskService.getTaskById(id).orElse(null)

    @PostMapping
    fun createTask(@RequestBody task: Task): Task = taskService.createTask(task)

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody taskDetails: Task): Task {
        return taskService.updateTask(id, taskDetails)
    }

    @PutMapping("/{id}/status")
    fun updateTaskCompleteness(@PathVariable id: Long, @RequestParam isCompleted: Boolean): Task {
        return taskService.updateTaskCompleteness(id, isCompleted)
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) {
        taskService.deleteTask(id)
    }

    @GetMapping("/today")
    fun getTodayTasks(@RequestParam completed: Boolean?): List<Task> {
        return if (completed != null) taskService.getTodayTasks(completed)
        else taskService.getTodayTasks()
    }

    @GetMapping("/current-week")
    fun getCurrentWeekTasks(@RequestParam completed: Boolean?): List<Task> {
        return if (completed != null) taskService.getCurrentWeekTasks(completed)
        else taskService.getCurrentWeekTasks()
    }

    @GetMapping("/current-month")
    fun getCurrentMonthTasks(@RequestParam completed: Boolean?): List<Task> {
        return if (completed != null) taskService.getCurrentMonthTasks(completed)
        else taskService.getCurrentMonthTasks()
    }
}