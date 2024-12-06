package org.sopt.and.data.dto.response

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
