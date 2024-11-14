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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.sopt.and.R
import org.sopt.and.core.common.modifier.defaultScreenBackground
import org.sopt.and.core.designsystem.component.layout.ContentSection
import org.sopt.and.core.designsystem.component.layout.HorizontalContentRow
import org.sopt.and.core.designsystem.component.layout.HorizontalPosterRow
import org.sopt.and.core.designsystem.component.layout.ImageWithRank

@Composable
fun HomeRoute(navController: NavController) {
    HomeScreen(navController)
}

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier.defaultScreenBackground(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            BannerView()
        }

        item {
            EditorRecommendationSection()
        }

        item {
            TopRankSection()
        }
    }
}

@Composable
private fun BannerView() {
    val pagerState = rememberPagerState(pageCount = { 3 })

    HorizontalPager(state = pagerState) {
        Image(
            painter = painterResource(R.drawable.banner1),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 32.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun EditorRecommendationSection() {
    ContentSection(
        title = stringResource(id = R.string.wavve_recommended),
        onMoreClick = { /* 더보기 처리 */ }
    ) {
        val recommendedItems = List(5) { R.drawable.img2 }

        HorizontalPosterRow(
            items = recommendedItems,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
    }
}

@Composable
private fun TopRankSection() {
    ContentSection(
        title = stringResource(id = R.string.today_top_rank),
        onMoreClick = { /* 더보기 처리 */ }
    ) {
        val rankItems = (1..20).toList()

        HorizontalContentRow(
            items = rankItems,
            itemContent = { rank ->
                ImageWithRank(
                    imageResId = R.drawable.img2,
                    rank = rank
                )
            },
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
    }
}