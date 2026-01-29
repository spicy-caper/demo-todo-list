package com.todo.list.infrastructure.web.todo

import com.todo.list.application.todo.TodoEventPublisher
import com.todo.list.domain.todo.Todo
import com.todo.list.domain.todo.TodoRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/todos")
class TodoController(
    private val repository: TodoRepository,
    private val publisher: TodoEventPublisher
) {

    @GetMapping
    fun getAll(): Mono<List<Todo>> =
        Mono.just(repository.findAll().toList())

    @PostMapping
    fun create(@RequestBody req: CreateTodoRequest): Mono<Todo> {
        val todo = repository.save(Todo(title = req.title))
        publisher.publish(repository.findAll().toList())
        return Mono.just(todo)
    }

    @PutMapping("/{id}/toggle")
    fun toggle(@PathVariable id: Long): Mono<Void>  {
        if (repository.existsById(id)) {
            val todo = repository.findById(id).get()
            todo.completed = todo.completed.not()
            repository.save(todo)
            publisher.publish(repository.findAll().toList())
        }
        return Mono.empty()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<Void> {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            publisher.publish(repository.findAll().toList())
        }
        return Mono.empty()
    }
}

data class CreateTodoRequest(val title: String)
