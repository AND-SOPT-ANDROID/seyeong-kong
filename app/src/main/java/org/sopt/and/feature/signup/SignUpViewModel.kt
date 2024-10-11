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

    fun onSignUpClick(email: String, password: String) {
        viewModelScope.launch {
            if (!isValidEmail(email)) {
                _signUpEvent.emit(SignUpEvent.Failure("올바른 이메일 형식이 아닙니다."))
                return@launch
            }

            if (!isValidPassword(password)) {
                _signUpEvent.emit(SignUpEvent.Failure("비밀번호는 8-20자 이내, 영문, 숫자, 특수문자 혼용해야 합니다."))
                return@launch
            }

            userRepository.saveUserInfo(email, password)
            _signUpEvent.emit(SignUpEvent.Success(email, password))
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { it in "~!@#$%^&*()_+=?/,.;:[]{}" }
        val isCorrectLength = password.length in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH
        return (hasUppercase || hasLowercase) && hasDigit && hasSpecialChar && isCorrectLength
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 20
    }
}
