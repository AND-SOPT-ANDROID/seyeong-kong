package org.sopt.and.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.ui.theme.DarkGray2
import org.sopt.and.ui.theme.DarkGray3
import org.sopt.and.R

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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray3),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            ProfileSection(userEmail = userEmail)
        }

        item {
            InterestsSection(title = "관심 프로그램")
        }

        item {
            InterestsSection(title = "관심 영화")
        }

        item {
            EditorPickSection()
        }

        item {
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
}

@Composable
fun ProfileSection(userEmail: String) {
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
                    .background(Gray)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "$userEmail 님",
                color = LightGray,
                fontSize = 16.sp
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "현재 보유하신 이용권이 없습니다.",
            color = LightGray,
            fontSize = 13.sp
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 12.dp),
            text = "구매하기 >",
            color = White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun InterestsSection(title: String) {
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 20.dp)
    ) {
        Text(
            text = title,
            color = White,
            fontSize = 20.sp,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(20) { index ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(140.dp)
                        .aspectRatio(3f / 4f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img2),
                        contentDescription = "Editor Recommendation $index",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun EditorPickSection(editorPicks: List<String> = emptyList()) {
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 20.dp)
    ) {
        Text(
            text = "관심 에디터 픽",
            color = White,
            fontSize = 20.sp,
        )

        Box(
            modifier = Modifier
                .height(150.dp)
                .padding(top = 8.dp)
        ) {
            if (editorPicks.isEmpty()) {
                Text(
                    text = "관심 에디터 픽이 없습니다.",
                    color = LightGray,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    items(editorPicks.size) { index ->
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(120.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img2),
                                    contentDescription = "Editor Pick $index",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "추천 $index",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                color = LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}
