package com.sopt.now.compose.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.R.drawable.ic_sign_up_profile_person
import com.sopt.now.compose.component.row.CustomTextRowPair
import com.sopt.now.compose.component.text.PageTitle
import com.sopt.now.compose.core.intent.getSafeParcelable
import com.sopt.now.compose.feature.model.User
import com.sopt.now.compose.feature.util.KeyStorage
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val receivedUserInput = getUserData()
                    MainScreen(user = receivedUserInput)
                }
            }
        }
    }

    private fun getUserData() =
        intent?.getSafeParcelable<User>(name = KeyStorage.USER_INPUT) ?: User(
            id = "배찬우",
            password = "12345678",
            nickName = "bcw",
            mbti = "infp"
        )
}

@Composable
fun MainScreen(user: User) {
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
                painter = painterResource(id = ic_sign_up_profile_person),
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NOWSOPTAndroidTheme {
        MainScreen(
            user = User(
                id = "dsfs",
                password = "dsfsdf",
                nickName = "df",
                mbti = "asdf"
            )
        )
    }
}
