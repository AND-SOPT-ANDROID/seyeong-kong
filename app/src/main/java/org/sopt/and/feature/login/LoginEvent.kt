package org.sopt.and.feature.login

sealed class LoginEvent {
    data class Success(val email: String) : LoginEvent()
    data class Failure(val message: String) : LoginEvent()
}