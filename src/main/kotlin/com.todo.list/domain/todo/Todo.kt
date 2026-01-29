package com.todo.list.domain.todo

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class Todo(
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    val id: Long?= null,
    val title: String,
    var completed: Boolean = false

)