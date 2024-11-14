package org.sopt.and.feature.login

sealed class LoginEvent {
   data class Success(val token: String) : LoginEvent()
   data class Failure(val message: String) : LoginEvent()
}