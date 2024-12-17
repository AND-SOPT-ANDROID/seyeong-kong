package org.sopt.and.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.sopt.and.R
import org.sopt.and.core.common.modifier.defaultScreenBackground
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.core.designsystem.component.layout.ContentSection
import org.sopt.and.core.designsystem.component.layout.HorizontalContentRow
import org.sopt.and.core.designsystem.component.layout.HorizontalPosterRow
import org.sopt.and.core.designsystem.component.layout.ImageWithRank
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.defaultScreenBackground(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            BannerView(
                banners = state.banners,
                onBannerClick = { bannerId ->
                    onIntent(HomeIntent.ClickBanner(bannerId))
                }
            )
        }

        item {
            EditorRecommendationSection(
                recommendedContents = state.recommendedContents,
                onMoreClick = {
                    onIntent(HomeIntent.ClickMoreRecommendations)
                }
            )
        }

        item {
            TopRankSection(
                rankContents = state.rankContents,
                onMoreClick = {
                    onIntent(HomeIntent.ClickMoreRankings)
                }
            )
        }
    }
}

@Composable
private fun BannerView(
    banners: List<Int>,
    onBannerClick: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { banners.size })

    HorizontalPager(state = pagerState) { page ->
        Image(
            painter = painterResource(banners[page]),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 32.dp)
                .noRippleClickable { onBannerClick(page) },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun EditorRecommendationSection(
    recommendedContents: List<Int>,
    onMoreClick: () -> Unit
) {
    ContentSection(
        title = stringResource(id = R.string.wavve_recommended),
        onMoreClick = onMoreClick
    ) {
        HorizontalPosterRow(
            items = recommendedContents,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        )
    }
}

@Composable
private fun TopRankSection(
    rankContents: List<Int>,
    onMoreClick: () -> Unit
) {
    ContentSection(
        title = stringResource(id = R.string.today_top_rank),
        onMoreClick = onMoreClick
    ) {
        HorizontalContentRow(
            items = rankContents,
            itemContent = { rank ->
                ImageWithRank(
                    imageResId = R.drawable.img2,
                    rank = rank,
                )
            },
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
    }
}
