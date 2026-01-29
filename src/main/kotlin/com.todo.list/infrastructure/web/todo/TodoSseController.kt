package com.todo.list.infrastructure.web.todo

import com.todo.list.application.todo.TodoEventPublisher
import com.todo.list.domain.todo.Todo
import com.todo.list.domain.todo.TodoRepository
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class TodoSseController(
    private val publisher: TodoEventPublisher,
    private val repository: TodoRepository
) {

    @GetMapping(
        value = ["/todos/stream"],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    fun streamTodos(): Flux<ServerSentEvent<List<Todo>>> {

        val initial = ServerSentEvent.builder(repository.findAll().toList())
            .event("init")
            .build()

        return publisher.stream()
            .map {
                ServerSentEvent.builder(it)
                    .event("update")
                    .build()
            }
            .startWith(initial)
    }
}
