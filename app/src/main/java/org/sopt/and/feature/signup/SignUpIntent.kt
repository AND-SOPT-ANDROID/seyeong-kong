package org.sopt.and.feature.signup

sealed class SignUpIntent {
    data class UpdateUsername(val username: String) : SignUpIntent()
    data class UpdatePassword(val password: String) : SignUpIntent()
    data class UpdateHobby(val hobby: String) : SignUpIntent()
    data object TogglePasswordVisibility : SignUpIntent()
    data object SignUp : SignUpIntent()
    data object ConsumeSignUpEvent : SignUpIntent()
}
