package com.sopt.now.compose.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.core.intent.getSafeParcelable
import com.sopt.now.compose.feature.model.User
import com.sopt.now.compose.feature.nav.BottomNavGraph
import com.sopt.now.compose.feature.nav.BottomNavigation
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
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            BottomNavGraph(navController = navController, user = user)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview222() {
    NOWSOPTAndroidTheme {
//        MainScreen()
    }
}
