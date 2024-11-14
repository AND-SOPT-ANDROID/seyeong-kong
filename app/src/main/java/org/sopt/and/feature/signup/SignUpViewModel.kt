package org.sopt.and.feature.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import org.sopt.and.data.UserRepository

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _signUpEvent = MutableSharedFlow<SignUpEvent>()
    val signUpEvent = _signUpEvent.asSharedFlow()

    fun onSignUpClick(username: String, password: String, hobby: String) {
        viewModelScope.launch {
            runCatching {
                userRepository.postSignUp(username, password, hobby)
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    userRepository.saveUserInfo(username, password, hobby)
                    _signUpEvent.emit(SignUpEvent.Success(username))
                } else {
                    _signUpEvent.emit(SignUpEvent.Failure("회원가입 실패: ${response.errorBody()?.string() ?: "알 수 없는 오류"}"))
                }
            }.onFailure { exception ->
                _signUpEvent.emit(SignUpEvent.Failure("회원가입 중 오류 발생: ${exception.message}"))
            }
        }
    }
}
