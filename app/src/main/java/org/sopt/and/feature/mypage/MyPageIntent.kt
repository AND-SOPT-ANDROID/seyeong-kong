package org.sopt.and.feature.mypage

sealed class MyPageIntent {
    data object LoadProfile : MyPageIntent()
    data object ClickMoreHistory : MyPageIntent()
    data object Logout : MyPageIntent()
}
