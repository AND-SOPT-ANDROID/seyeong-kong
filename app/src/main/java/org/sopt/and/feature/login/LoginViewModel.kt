package org.sopt.and.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.data.repository.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateUsername -> updateUsername(intent.username)
            is LoginIntent.UpdatePassword -> updatePassword(intent.password)
            is LoginIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            is LoginIntent.Login -> login()
            is LoginIntent.UpdateUsernameFocusState -> updateUsernameFocusState(intent.isFocused)
            is LoginIntent.UpdatePasswordFocusState -> updatePasswordFocusState(intent.isFocused)
            is LoginIntent.ConsumeLoginEvent -> consumeLoginEvent()
        }
    }

    private fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    private fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    private fun updateUsernameFocusState(isFocused: Boolean) {
        _uiState.update {
            it.copy(isUsernameErrorVisible = isFocused && it.username.isEmpty())
        }
    }

    private fun updatePasswordFocusState(isFocused: Boolean) {
        _uiState.update {
            it.copy(isPasswordErrorVisible = isFocused && it.password.isEmpty())
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentState = _uiState.value
            userRepository.login(currentState.username, currentState.password)
                .onSuccess { token ->
                    _uiState.update {
                        it.copy(
                            loginEvent = LoginEvent.Success(token),
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            loginEvent = LoginEvent.Failure(
                                exception.message ?: "알 수 없는 오류가 발생했습니다."
                            ),
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun consumeLoginEvent() {
        _uiState.update { it.copy(loginEvent = null) }
    }
}
