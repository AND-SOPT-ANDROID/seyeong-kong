package org.sopt.and.feature.mypage

data class MyPageState(
    val hobby: String? = null,
    val username: String? = null,
    val isLoading: Boolean = false,
    val viewHistoryList: List<Int> = emptyList(),
    val interestedProgramList: List<Int> = emptyList(),
    val interestedMovieList: List<Int> = emptyList(),
    val editorPickList: List<Int> = emptyList()
)
