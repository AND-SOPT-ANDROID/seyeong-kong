package org.sopt.and.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.ui.theme.DarkGray3

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
fun MyPageScreen(userEmail: String, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray3)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(White)
            )

            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "$userEmail!",
                color = LightGray,
                fontSize = 24.sp
            )
        }


        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "로그아웃",
            color = Gray,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(16.dp)
                .noRippleClickable { onLogout() }
        )
    }
}