package org.sopt.and.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.sopt.and.data.UserRepository

class MyPageViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _hobby = MutableStateFlow<String?>(null)
    val hobby: StateFlow<String?> = _hobby

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadHobby()
    }

    private fun loadHobby() {
        viewModelScope.launch {
            _loading.value = true
            val token = userRepository.getToken() ?: run {
                _hobby.value = "로그인이 필요합니다"
                _loading.value = false
                return@launch
            }

            runCatching {
                userRepository.getHobby(token)
            }.onSuccess { response ->
                _hobby.value = when {
                    response.isSuccessful && response.body()?.result?.hobby != null -> {
                        response.body()?.result?.hobby
                    }
                    else -> "취미 정보를 가져올 수 없습니다"
                }
            }.onFailure {
                _hobby.value = "네트워크 오류가 발생했습니다"
            }

            _loading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
