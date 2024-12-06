package org.sopt.and.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.sopt.and.R
import org.sopt.and.core.common.modifier.defaultScreenBackground
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.core.designsystem.component.layout.InterestsSection
import org.sopt.and.ui.theme.DarkGray2

@Composable
fun MyPageRoute(
    navController: NavController,
    viewModel: MyPageViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        state = uiState,
        onLogout = {
            viewModel.logout()
            navController.navigate("login") {
                popUpTo("mypage") { inclusive = true }
            }
        }
    )
}

@Composable
fun MyPageScreen(
    state: MyPageState,
    onLogout: () -> Unit,
    viewHistoryList: List<Int> = emptyList(),
    interestedProgramList: List<Int> = emptyList(),
    interestedMovieList: List<Int> = emptyList(),
    editorPickList: List<Int> = emptyList()
) {
    val interestsSections = listOf(
        InterestsSectionData(
            titleResId = R.string.full_view_history,
            emptyMessageResId = R.string.no_full_view_history,
            interests = viewHistoryList,
            maxItems = 20
        ),
        InterestsSectionData(
            titleResId = R.string.interested_program,
            emptyMessageResId = R.string.no_interested_program,
            interests = interestedProgramList,
            maxItems = 1
        ),
        InterestsSectionData(
            titleResId = R.string.interested_movie,
            emptyMessageResId = R.string.no_interested_movie,
            interests = interestedMovieList
        ),
        InterestsSectionData(
            titleResId = R.string.interested_editor_pick,
            emptyMessageResId = R.string.empty_editor_pick,
            interests = editorPickList,
            showMoreButton = false
        )
    )

    LazyColumn(
        modifier = Modifier.defaultScreenBackground(),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            ProfileSection(
                hobby = state.hobby ?: "취미가 없습니다",
                isLoading = state.isLoading
            )
        }

        interestsSections.forEach { sectionData ->
            item {
                Spacer(modifier = Modifier.padding(top = 20.dp))
                InterestsSection(
                    title = stringResource(sectionData.titleResId),
                    emptyMessage = stringResource(sectionData.emptyMessageResId),
                    interests = sectionData.interests.take(sectionData.maxItems),
                    showMoreButton = sectionData.showMoreButton,
                    onMoreClick = { /* 더보기 처리 */ }
                )
            }
        }

        item {
            LogoutButton(onClick = onLogout)
        }
    }
}

@Composable
private fun ProfileSection(hobby: String, isLoading: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkGray2)
            .padding(top = 16.dp, start = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            if (isLoading) {
                Text(
                    text = "로딩 중...",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = stringResource(R.string.user_hobby_format, hobby),
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }
        }

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.no_subscription),
            color = Color.LightGray,
            fontSize = 13.sp
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 12.dp),
            text = stringResource(R.string.buy_subscription),
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Text(
        text = stringResource(R.string.logout),
        color = Color.Gray,
        modifier = Modifier
            .navigationBarsPadding()
            .padding(16.dp)
            .noRippleClickable(onClick = onClick)
    )
}
