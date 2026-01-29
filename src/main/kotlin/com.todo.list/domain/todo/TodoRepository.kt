package com.todo.list.domain.todo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: CrudRepository<Todo, Long>
