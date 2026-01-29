package com.todo.list.application.todo

import com.todo.list.domain.todo.Todo
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Component
class TodoEventPublisher {

    private val sink =
        Sinks.many().multicast().onBackpressureBuffer<List<Todo>>()

    fun publish(todos: List<Todo>) {
        sink.tryEmitNext(todos)
    }

    fun stream(): Flux<List<Todo>> =
        sink.asFlux()
}
