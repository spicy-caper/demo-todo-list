package com.todo.list.infrastructure.web.todo

import com.todo.list.application.todo.TodoService
import com.todo.list.domain.todo.Todo
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/todos")
class TodoController(
    private val service: TodoService,
) {

    @GetMapping
    fun getAll(): Mono<List<Todo>> =
        Mono.just(service.findAll().toList())

    @PostMapping
    fun create(@RequestBody req: CreateTodoRequest): Mono<Todo> {
        val todo = service.create(req.title)
        return Mono.just(todo)
    }

    @PutMapping("/{id}/toggle")
    fun toggle(@PathVariable id: Long): Mono<Void>  {
        service.toggle(id)
        return Mono.empty()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<Void> {
        service.delete(id)
        return Mono.empty()
    }
}

data class CreateTodoRequest(val title: String)
