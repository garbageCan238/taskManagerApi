import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*
import com.github.ryamal.taskmanagerapi.model.Task
import com.github.ryamal.taskmanagerapi.repository.TaskRepository
import com.github.ryamal.taskmanagerapi.service.TaskService
import com.github.ryamal.taskmanagerapi.utils.DateUtil

@ExtendWith(MockitoExtension::class)
class TaskServiceTest {

    @Mock
    private lateinit var taskRepository: TaskRepository

    @InjectMocks
    private lateinit var taskService: TaskService

    @Test
    fun `getAllTasks should return all tasks`() {
        val tasks = listOf(
            Task(id = 1, title = "Task 1", description = "Description 1", completed = false, creationDate = Date()),
            Task(id = 2, title = "Task 2", description = "Description 2", completed = true, creationDate = Date())
        )
        whenever(taskRepository.findAll()).thenReturn(tasks)

        val result = taskService.getAllTasks()

        assertEquals(tasks, result)
    }

    @Test
    fun `getTaskById should return task when task exists`() {
        val task = Task(id = 1, title = "Task 1", description = "Description 1", completed = false, creationDate = Date())
        whenever(taskRepository.findById(1)).thenReturn(Optional.of(task))

        val result = taskService.getTaskById(1)

        assertTrue(result.isPresent)
        assertEquals(task, result.get())
    }

    @Test
    fun `getTaskById should return empty when task does not exist`() {
        whenever(taskRepository.findById(1)).thenReturn(Optional.empty())

        val result = taskService.getTaskById(1)

        assertFalse(result.isPresent)
    }

    @Test
    fun `createTask should save and return task`() {
        val task = Task(id = null, title = "Task 1", description = "Description 1", completed = false, creationDate = Date())
        val savedTask = task.copy(id = 1)
        whenever(taskRepository.save(task)).thenReturn(savedTask)

        val result = taskService.createTask(task)

        verify(taskRepository).save(task)
        assertEquals(savedTask, result)
    }

    @Test
    fun `updateTask should update and return task when task exists`() {
        val task = Task(id = 1, title = "Updated Task", description = "Updated Description", completed = true, creationDate = Date())
        whenever(taskRepository.existsById(1)).thenReturn(true)
        whenever(taskRepository.save(task)).thenReturn(task)

        val result = taskService.updateTask(1, task)

        assertEquals(task, result)
        verify(taskRepository).save(task)
    }

    @Test
    fun `updateTask should throw exception when task does not exist`() {
        val task = Task(id = 1, title = "Updated Task", description = "Updated Description", completed = true, creationDate = Date())
        whenever(taskRepository.existsById(1)).thenReturn(false)

        assertThrows<RuntimeException> {
            taskService.updateTask(1, task)
        }
    }

    @Test
    fun `deleteTask should delete task when task exists`() {
        whenever(taskRepository.existsById(1)).thenReturn(true)

        taskService.deleteTask(1)

        verify(taskRepository).deleteById(1)
    }

    @Test
    fun `deleteTask should throw exception when task does not exist`() {
        whenever(taskRepository.existsById(1)).thenReturn(false)

        assertThrows<RuntimeException> {
            taskService.deleteTask(1)
        }
    }

    @Test
    fun `updateTaskCompleteness should update completeness and return task`() {
        val task = Task(id = 1, title = "Task 1", description = "Description 1", completed = false, creationDate = Date())
        val updatedTask = task.copy(completed = true)
        whenever(taskRepository.findById(1)).thenReturn(Optional.of(task))
        whenever(taskRepository.save(updatedTask)).thenReturn(updatedTask)

        val result = taskService.updateTaskCompleteness(1, true)

        assertTrue(result.completed)
        verify(taskRepository).save(updatedTask)
    }

    @Test
    fun `getTodayTasks should return tasks for today`() {
        val startOfDay = DateUtil.getStartOfDay()
        val endOfDay = DateUtil.getEndOfDay()
        val tasks = listOf(
            Task(id = 1, title = "Task 1", description = "Description 1", completed = false, creationDate = Date())
        )
        whenever(taskRepository.findByCreationDateBetween(startOfDay, endOfDay)).thenReturn(tasks)

        val result = taskService.getTodayTasks()

        assertEquals(tasks, result)
    }

}
