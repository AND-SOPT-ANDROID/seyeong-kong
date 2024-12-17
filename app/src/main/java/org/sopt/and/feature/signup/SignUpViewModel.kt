package org.sopt.and.feature.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.sopt.and.data.repository.UserRepository

class SignUpViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState

    fun handleIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UpdateUsername -> updateUsername(intent.username)
            is SignUpIntent.UpdatePassword -> updatePassword(intent.password)
            is SignUpIntent.UpdateHobby -> updateHobby(intent.hobby)
            is SignUpIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignUpIntent.SignUp -> signUp()
            is SignUpIntent.ConsumeSignUpEvent -> consumeSignUpEvent()
        }
    }

    private fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    private fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun updateHobby(hobby: String) {
        _uiState.update { it.copy(hobby = hobby) }
    }

    private fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    private fun signUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentState = _uiState.value
            userRepository.signUp(
                username = currentState.username,
                password = currentState.password,
                hobby = currentState.hobby
            ).onSuccess { userEntity ->
                _uiState.update {
                    it.copy(
                        signUpEvent = SignUpEvent.Success(userEntity.username),
                        isLoading = false
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        signUpEvent = SignUpEvent.Failure(exception.message ?: "회원가입 중 오류가 발생했습니다"),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun consumeSignUpEvent() {
        _uiState.update { it.copy(signUpEvent = null) }
    }
}
