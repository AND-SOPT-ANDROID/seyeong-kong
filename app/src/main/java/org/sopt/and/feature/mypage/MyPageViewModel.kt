package org.sopt.and.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.data.repository.UserRepository

class MyPageViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageState())
    val uiState: StateFlow<MyPageState> = _uiState

    init {
        loadHobby()
        loadUserInfo()
    }

    private fun loadHobby() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val token = userRepository.getToken() ?: run {
                _uiState.update {
                    it.copy(
                        hobby = "로그인이 필요합니다",
                        isLoading = false
                    )
                }
                return@launch
            }

            userRepository.getHobby(token)
                .onSuccess { hobby ->
                    _uiState.update {
                        it.copy(hobby = hobby)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(hobby = error.message ?: "네트워크 오류가 발생했습니다")
                    }
                }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo()
                .onSuccess { userEntity ->
                    _uiState.update {
                        it.copy(username = userEntity.username)
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _uiState.update {
                it.copy(
                    hobby = "로그인이 필요합니다",
                    username = null
                )
            }
        }
    }
}
