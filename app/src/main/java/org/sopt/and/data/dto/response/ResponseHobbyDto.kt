package org.sopt.and.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHobbyDto(
    @SerialName("result")
    val result: HobbyResult? = null,
    @SerialName("code")
    val code: String? = null
)

@Serializable
data class HobbyResult(
    @SerialName("hobby")
    val hobby: String
)
