package org.sopt.and.feature.login

sealed class LoginIntent {
    data class UpdateUsername(val username: String) : LoginIntent()
    data class UpdatePassword(val password: String) : LoginIntent()
    data object TogglePasswordVisibility : LoginIntent()
    data object Login : LoginIntent()
    data class UpdateUsernameFocusState(val isFocused: Boolean) : LoginIntent()
    data class UpdatePasswordFocusState(val isFocused: Boolean) : LoginIntent()
    data object ConsumeLoginEvent : LoginIntent()
}
