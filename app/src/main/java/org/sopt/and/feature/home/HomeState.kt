package org.sopt.and.feature.home

data class HomeState(
    val banners: List<Int> = emptyList(),
    val recommendedContents: List<Int> = emptyList(),
    val rankContents: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
