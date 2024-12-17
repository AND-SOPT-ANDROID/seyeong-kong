package org.sopt.and.feature.signup

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val hobby: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val signUpEvent: SignUpEvent? = null
)
