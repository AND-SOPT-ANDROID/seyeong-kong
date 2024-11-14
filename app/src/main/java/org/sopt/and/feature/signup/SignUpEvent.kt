package org.sopt.and.feature.signup

sealed class SignUpEvent {
    data class Success(val username: String) : SignUpEvent()
    data class Failure(val message: String) : SignUpEvent()
}