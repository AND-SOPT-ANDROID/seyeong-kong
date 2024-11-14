package org.sopt.and.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val result: Result? = null,
    val code: String? = null
) {
    @Serializable
    data class Result(
        val no: Int? = null,
        val token: String? = null
    )
}