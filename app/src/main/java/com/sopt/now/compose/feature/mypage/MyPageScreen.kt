package com.sopt.now.compose.feature.mypage

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.ui.component.row.CustomTextRowPair
import com.sopt.now.compose.ui.component.text.PageTitle
import com.sopt.now.compose.ui.core.factory.ViewModelFactory
import com.sopt.now.compose.ui.core.view.UiState

@Composable
fun MyPageScreen(id: Int) {
    val viewModel: MyPageViewModel = viewModel(factory = ViewModelFactory())
    val context = LocalContext.current

    val memberInfoState =
        viewModel.getMemberInfoState.collectAsState(initial = UiState.Loading).value

    val user = when (memberInfoState) {
        is UiState.Success -> memberInfoState.data
        is UiState.Failure -> {
            Toast.makeText(context, memberInfoState.errorMessage, Toast.LENGTH_SHORT).show()
            null
        }

        else -> null
    }

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
            user?.let {
                CustomTextRowPair(name1 = "아이디", name2 = it.id)
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextRowPair(name1 = "비밀번호", name2 = it.password)
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextRowPair(name1 = "닉네임", name2 = it.nickName)
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextRowPair(name1 = "phone", name2 = it.phone)
            }
        }
    }
}
