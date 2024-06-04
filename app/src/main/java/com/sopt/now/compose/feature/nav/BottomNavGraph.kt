package com.sopt.now.compose.feature.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sopt.now.compose.feature.home.HomeScreen
import com.sopt.now.compose.feature.mypage.MyPageScreen
import com.sopt.now.compose.feature.search.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController, id: Int) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen()
        }
        composable(BottomNavItem.Search.screenRoute) {
            SearchScreen()
        }
        composable(BottomNavItem.MyPage.screenRoute) {
            MyPageScreen(id = id)
        }
    }
}
