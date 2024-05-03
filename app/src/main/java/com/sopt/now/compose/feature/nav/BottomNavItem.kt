package com.sopt.now.compose.feature.nav

import androidx.annotation.DrawableRes
import com.sopt.now.compose.R
import com.sopt.now.compose.feature.util.KeyStorage

sealed class BottomNavItem(
    val label: Int,
    @DrawableRes val icon: Int,
    val screenRoute: String
) {
    data object Home :
        BottomNavItem(
            R.string.nav_home,
            R.drawable.ic_home_black_24,
            KeyStorage.HOME
        )

    data object Search :
        BottomNavItem(
            R.string.nav_search,
            R.drawable.ic_search_gray_24,
            KeyStorage.SEARCH
        )

    data object MyPage :
        BottomNavItem(
            R.string.nav_my_page,
            R.drawable.ic_my_page_black_24,
            KeyStorage.MY_PAGE
        )
}
