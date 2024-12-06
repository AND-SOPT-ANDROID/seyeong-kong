package org.sopt.and.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.sopt.and.data.repository.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    fun onLoginClick(username: String, password: String) {
        viewModelScope.launch {
            userRepository.login(username, password)
                .onSuccess { token ->
                    _loginEvent.emit(LoginEvent.Success(token))
                }
                .onFailure { exception ->
                    _loginEvent.emit(LoginEvent.Failure(exception.message ?: "알 수 없는 오류가 발생했습니다."))
                }
        }
    }
}
