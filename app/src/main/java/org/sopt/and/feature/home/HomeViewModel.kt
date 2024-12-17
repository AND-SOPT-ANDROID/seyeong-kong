package org.sopt.and.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.R

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    init {
        handleIntent(HomeIntent.LoadHomeContent)
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadHomeContent -> loadHomeContent()
            is HomeIntent.ClickBanner -> { /* Handle banner click */ }
            is HomeIntent.ClickRecommendation -> { /* Handle recommendation click */ }
            is HomeIntent.ClickRankContent -> { /* Handle rank content click */ }
            is HomeIntent.ClickMoreRecommendations -> { /* Handle more recommendations */ }
            is HomeIntent.ClickMoreRankings -> { /* Handle more rankings */ }
        }
    }

    private fun loadHomeContent() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            _uiState.update {
                it.copy(
                    banners = List(3) { R.drawable.banner1 },
                    recommendedContents = List(5) { R.drawable.img2 },
                    rankContents = (1..20).toList(),
                    isLoading = false
                )
            }
        }
    }
}
