package org.sopt.and.feature.mypage

data class InterestsSectionData(
    val titleResId: Int,
    val emptyMessageResId: Int,
    val interests: List<Int>,
    val showMoreButton: Boolean = true,
    val maxItems: Int = Int.MAX_VALUE
)
