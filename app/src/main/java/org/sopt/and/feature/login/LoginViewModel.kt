package org.sopt.and.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.sopt.and.data.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    fun onLoginClick(email: String, password: String) {
        viewModelScope.launch {
            val savedEmail = userRepository.getEmail()
            val savedPassword = userRepository.getPassword()

            if (email != savedEmail) {
                _loginEvent.emit(LoginEvent.Failure("이메일이 다릅니다."))
                return@launch
            }

            if (password != savedPassword) {
                _loginEvent.emit(LoginEvent.Failure("비밀번호가 틀렸습니다."))
                return@launch
            }

            _loginEvent.emit(LoginEvent.Success(email))
        }
    }
}