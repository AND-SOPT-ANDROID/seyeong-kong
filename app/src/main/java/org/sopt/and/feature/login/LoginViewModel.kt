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

    fun onLoginClick(username: String, password: String) {
        viewModelScope.launch {
            runCatching {
                userRepository.postLogin(username, password)
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()?.result?.token
                    if (token != null) {
                        userRepository.saveUserToken(token)
                        _loginEvent.emit(LoginEvent.Success(token))
                    } else {
                        _loginEvent.emit(LoginEvent.Failure("토큰을 받아오지 못했습니다."))
                    }
                }  else {
                    _loginEvent.emit(LoginEvent.Failure("로그인 실패: ${response.errorBody()?.string() ?: "알 수 없는 오류"}"))
                }
            }.onFailure { exception ->
                _loginEvent.emit(LoginEvent.Failure("네트워크 오류 발생: ${exception.message}"))
            }
        }
    }
}