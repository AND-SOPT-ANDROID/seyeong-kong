package org.sopt.and.feature.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isUsernameErrorVisible: Boolean = false,
    val isPasswordErrorVisible: Boolean = false,
    val isLoading: Boolean = false,
    val loginEvent: LoginEvent? = null
)
