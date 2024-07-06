package com.github.ryamal.taskmanagerapi.model

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(length = 2000, nullable = true)
    val description: String? = null,

    @Column(nullable = false)
    val completed: Boolean = false,

    @Temporal(TemporalType.TIMESTAMP)
    val creationDate: Date? = null,
)
