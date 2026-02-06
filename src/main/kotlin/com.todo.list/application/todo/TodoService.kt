package com.todo.list.application.todo

import com.todo.list.domain.todo.Todo
import com.todo.list.domain.todo.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val repository: TodoRepository,
    private val publisher: TodoEventPublisher
) {
    fun findAll() = repository.findAll().toList()

    fun create(title: String): Todo {
        val todo = repository.save(Todo(title = title))
        publisher.publish(repository.findAll().toList())
        return todo
    }

    fun toggle(id: Long): Boolean  {
        if (repository.existsById(id)) {
            val todo = repository.findById(id).get()
            todo.completed = todo.completed.not()
            repository.save(todo)
            publisher.publish(repository.findAll().toList())
            return true
        }
        return false
    }

    fun delete(id: Long): Boolean {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            publisher.publish(repository.findAll().toList())
            return true
        }
        return false
    }
}
