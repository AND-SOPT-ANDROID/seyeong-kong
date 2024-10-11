package org.sopt.and.feature.mypage

import androidx.lifecycle.ViewModel
import org.sopt.and.data.UserRepository

class MyPageViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userEmail: String
        get() = userRepository.getEmail().toString()

    fun logout() {
        userRepository.logout()
    }
}
