package org.sopt.and.domain.entity

data class UserEntity(
    val username: String,
    val password: String,
    val hobby: String,
    val token: String? = null
)