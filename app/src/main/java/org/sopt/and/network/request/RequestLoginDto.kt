package org.sopt.and.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)