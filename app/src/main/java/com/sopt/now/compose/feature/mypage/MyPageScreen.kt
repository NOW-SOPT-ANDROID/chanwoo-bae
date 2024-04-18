package com.sopt.now.compose.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.R
import com.sopt.now.compose.component.row.CustomTextRowPair
import com.sopt.now.compose.component.text.PageTitle
import com.sopt.now.compose.feature.model.User

@Composable
fun MyPageScreen(user: User) {
    Scaffold(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        topBar = {
            PageTitle("마이 페이지")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_sign_up_profile_person),
                contentDescription = "profile",
                modifier = Modifier
                    .size(130.dp)
                    .aspectRatio(1f / 1f)
                    .align(Alignment.CenterHorizontally)

            )
            Spacer(modifier = Modifier.height(40.dp))
            CustomTextRowPair(name1 = "아이디", name2 = user.id)
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextRowPair(name1 = "비밀번호", name2 = user.password)
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextRowPair(name1 = "닉네임", name2 = user.nickName)
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextRowPair(name1 = "mbti", name2 = user.mbti)
        }
    }
}
