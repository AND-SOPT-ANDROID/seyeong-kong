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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.sopt.and.R
import org.sopt.and.core.common.modifier.defaultScreenBackground
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.core.designsystem.component.layout.InterestsSection
import org.sopt.and.ui.theme.DarkGray2

@Composable
fun MyPageRoute(navController: NavController, viewModel: MyPageViewModel = viewModel()) {
    val userEmail = viewModel.userEmail

    MyPageScreen(
        userEmail = userEmail,
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
    userEmail: String,
    onLogout: () -> Unit,
    viewHistoryList: List<Int> = emptyList(),
    interestedProgramList: List<Int> = emptyList(),
    interestedMovieList: List<Int> = emptyList(),
    editorPickList: List<Int> = emptyList()
) {
    LazyColumn(
        modifier = Modifier.defaultScreenBackground(),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            ProfileSection(userEmail = userEmail)
        }

        item {
            InterestsSection(
                title = stringResource(R.string.full_view_history),
                emptyMessage = stringResource(R.string.no_full_view_history),
                interests = viewHistoryList,
                showMoreButton = true,
                onMoreClick = { /* 더보기 처리 */ },
                itemCount = 20
            )
        }

        item {
            InterestsSection(
                title = stringResource(R.string.interested_program),
                emptyMessage = stringResource(R.string.no_interested_program),
                interests = interestedProgramList,
                showMoreButton = true,
                onMoreClick = { /* 더보기 처리 */ },
                itemCount = 1
            )
        }

        item {
            InterestsSection(
                title = stringResource(R.string.interested_movie),
                emptyMessage = stringResource(R.string.no_interested_movie),
                interests = interestedMovieList,
                showMoreButton = true,
                onMoreClick = { /* 더보기 처리 */ }
            )
        }

        item {
            InterestsSection(
                title = stringResource(R.string.interested_editor_pick),
                emptyMessage = stringResource(R.string.empty_editor_pick),
                interests = editorPickList,
                showMoreButton = false
            )
        }

        item {
            LogoutButton(onClick = onLogout)
        }
    }
}

@Composable
private fun ProfileSection(userEmail: String) {
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
            Text(
                text = stringResource(R.string.user_name_format, userEmail),
                color = Color.LightGray,
                fontSize = 16.sp
            )
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