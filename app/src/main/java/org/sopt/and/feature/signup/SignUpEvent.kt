package org.sopt.and.feature.signup

sealed class SignUpEvent {
    data class Success(val email: String, val password: String) : SignUpEvent()
    data class Failure(val message: String) : SignUpEvent()
}