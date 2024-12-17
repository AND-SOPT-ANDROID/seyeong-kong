package org.sopt.and.feature.home

sealed class HomeIntent {
    data object LoadHomeContent : HomeIntent()
    data class ClickBanner(val bannerId: Int) : HomeIntent()
    data class ClickRecommendation(val contentId: Int) : HomeIntent()
    data class ClickRankContent(val contentId: Int) : HomeIntent()
    data object ClickMoreRecommendations : HomeIntent()
    data object ClickMoreRankings : HomeIntent()
}
